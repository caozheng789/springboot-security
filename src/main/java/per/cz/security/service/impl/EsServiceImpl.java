package per.cz.security.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.jdbc.Wrapper;
import com.qiniu.util.Json;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.cz.security.entity.ArticleInfo;
import per.cz.security.entity.bean.ElasticsearchBean;
import per.cz.security.mapper.ArticleMapper;
import per.cz.security.result.ResultData;
import per.cz.security.service.EsServiceI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/10 17:14
 */
@Service
public class EsServiceImpl implements EsServiceI {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ElasticsearchBean client;

    @Override
    public ResultData bulk() {
        List<ArticleInfo> articleInfos = articleMapper.selectList(new QueryWrapper<ArticleInfo>());

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        // 批处理请求
        for (int i = 0; i < articleInfos.size() ; i++) {
            // 批量更新和批量删除，就在这里修改对应的请求就可以了
            bulkRequest.add( new IndexRequest("art")
                    .id(""+(i+1))
                    .source(JSON.toJSONString(articleInfos.get(i)), XContentType.JSON));
        }
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = client.restHighLevelClient().bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return ResultData.error(e.getMessage());
        }
        System.out.println(bulkResponse.hasFailures());
        // 是否失败，返回 false 代表 成功！
        return ResultData.success(bulkResponse.hasFailures());
    }

    @Override
    public ResultData addIndex(String indexName) {
        // 1、创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 2、客户端执行请求 IndicesClient,请求后获得响应
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.restHighLevelClient()
                    .indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return ResultData.error(e.getMessage());
        }
        System.out.println(createIndexResponse);
        return ResultData.success(createIndexResponse);
    }

    @Override
    public ResultData getIndexExist(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);
        boolean exists = false;
        try {
            exists = client.restHighLevelClient().indices()
                    .exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return ResultData.error(e.getMessage());
        }
        System.out.println(exists);
        return ResultData.success(exists);
    }

    @Override
    public ResultData deleteIndex(String indexName) {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        // 删除
        AcknowledgedResponse delete = null;
        try {
            delete = client.restHighLevelClient().indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return ResultData.error(e.getMessage());
        }
        System.out.println(delete.isAcknowledged());
        return ResultData.success(delete.isAcknowledged());
    }

    @Override
    public ResultData addDocument(String indexName, ArticleInfo articleInfo) {
        // 创建请求
        IndexRequest request = new IndexRequest(indexName);
        // 规则 put /kuang_index/_doc/1
//        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        // 将我们的数据放入请求 json
        request.source(JSON.toJSONString(articleInfo), XContentType.JSON);
        // 客户端发送请求 , 获取响应的结果
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.restHighLevelClient().index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return ResultData.error(e.getMessage());
        }
        System.out.println(indexResponse.toString());
        // System.out.println(indexResponse.status());
        // 对应我们命令返回的状态 CREATED
        return ResultData.success(indexResponse.toString());
    }

    @Override
    public ResultData updateDocument(String indexName,String indexId,ArticleInfo article) {
        UpdateRequest updateRequest = new UpdateRequest(indexName,indexId);
        updateRequest.timeout("1s");

        updateRequest.doc(JSON.toJSONString(article),XContentType.JSON);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = client.restHighLevelClient().update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return ResultData.error(e.getMessage());
        }
        System.out.println(updateResponse.status());
        return ResultData.success(updateResponse.status());
    }


    @Override
    public ResultData deleteDoc(String indexName, String indexId) {
        DeleteRequest request = new DeleteRequest(indexName,indexId);
        request.timeout("1s");
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = client.restHighLevelClient().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return ResultData.error(e.getMessage());
        }
        System.out.println(deleteResponse.status());
        return ResultData.success(deleteResponse.status());
    }

    @Override
    public ResultData search(String keyword, Integer pageNum, Integer pageSize) {
        SearchRequest searchRequest = new SearchRequest("art");
        // 构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询条件，我们可以使用 QueryBuilders 工具来实现
        // 分页
        sourceBuilder.from(pageNum);
        sourceBuilder.size(pageSize);
        // 精准匹配
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", keyword);
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        //关闭多个高亮
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        //执行搜索
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = client.restHighLevelClient()
                    .search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return ResultData.error(e.getMessage());
        }
        System.out.println(JSON.toJSON(searchResponse));
        //解析结果
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            if (null != title){
                Text[] fragments = title.getFragments();
                String n_title = "";
                for (Text text : fragments){
                    n_title += text;
                }
                sourceAsMap.put("title",n_title);
            }
            mapList.add(sourceAsMap);
        }
        return ResultData.success(mapList);
    }
}
