package per.cz.security.controller;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.cz.security.entity.ArticleInfo;
import per.cz.security.entity.bean.ElasticsearchBean;
import per.cz.security.result.ResultData;
import per.cz.security.service.EsServiceI;

import java.io.IOException;

/**
 * @author : zheng
 * @version : 1.0
 * @desc : es 操作
 * @date : 2020/6/8 10:18
 */
@RestController
@RequestMapping("esController")
public class EsController {

    @Autowired
    ElasticsearchBean client;

    @Autowired
    EsServiceI esService;


    /**
     * Request PUT
     * 测试索引的创建
     */
    @PutMapping
    public ResultData testCreateIndex(String indexName) throws IOException {
       return esService.addIndex(indexName);
    }


    /**
     * 测试获取索引,判断其是否存在 @Test
     * @throws IOException
     */
    @GetMapping("getIndexExist")
    public ResultData getIndexExist(String indexName) throws IOException {
        return esService.getIndexExist(indexName);
    }


    /**
     *  删除索引
     */
    @DeleteMapping
    public ResultData deleteIndex(String indexName) throws IOException {
        return esService.deleteIndex(indexName);
    }

    /**
     *  测试添加文档 @Test
     * @throws IOException
     */
    @PutMapping("addDocument")
    public ResultData addDocument(String indexName, ArticleInfo articleInfo) throws IOException {
       return esService.addDocument(indexName,articleInfo);
    }


    /**
     * 获取文档，判断是否存在 get /index/doc/1 @Test
     */
    @GetMapping("docExists")
    public void docExists() throws IOException {
        GetRequest getRequest = new GetRequest("kuang_index", "1");
        // 不获取返回的 _source 的上下文了
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = client.restHighLevelClient().exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }


    /**
     * 获得文档的信息 @Test
     */
    @GetMapping("getDocument")
    public void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest("kuang_index", "1");
        GetResponse getResponse = client.restHighLevelClient().get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());
        // 打印文档的内容
        System.out.println(getResponse);
        // 返回的全部内容和命令式一样的
    }

    /**
     * 更新文档的信息
     */
    @PostMapping
    public ResultData updateDocument(String indexName,String indexId,ArticleInfo article) {
        return esService.updateDocument(indexName,indexId,article);
    }

    /**
     * 删除文档记录
     */
    @DeleteMapping("docDel")
    public ResultData deleteDoc(String indexName,String indexId) {
        return esService.deleteDoc(indexName, indexId);
    }

    /**
     * 特殊的，真的项目一般都会批量插入数据！ @Test
     * @throws IOException
     */
    @PutMapping("bulk")
    public ResultData testBulkRequest()  {
        return esService.bulk();
    }


    /**
     *  查询
     */
    @GetMapping("search")
    public ResultData search(String keyword,Integer pageNum,Integer pageSize)  {
        return esService.search(keyword,pageNum,pageSize);
    }
}
