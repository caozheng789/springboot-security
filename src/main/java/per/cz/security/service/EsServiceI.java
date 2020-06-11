package per.cz.security.service;

import per.cz.security.entity.ArticleInfo;
import per.cz.security.result.ResultData;

/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/10 17:13
 */
public interface EsServiceI {

    /**
     * 同步mysql中数据
     * @return
     */
    ResultData bulk();

    /**
     * 创建索引
     * @param indexName
     * @return
     */
    ResultData addIndex(String indexName);

    /**
     * 查询索引是否存在
     * @param indexName
     * @return
     */
    ResultData getIndexExist(String indexName);

    /**
     * 删除索引
     * @param indexName
     * @return
     */
    ResultData deleteIndex(String indexName);

    /**
     * 添加文档
     * @return
     */
    ResultData addDocument(String indexName, ArticleInfo articleInfo);

    /**
     * 修改文档
     * @return
     */
    ResultData updateDocument(String indexName,String indexId,ArticleInfo article);

    /**
     * 删除文档
     * @param indexName
     * @param indexId
     * @return
     */
    ResultData deleteDoc(String indexName, String indexId);

    /**
     * 查询
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultData search(String keyword, Integer pageNum, Integer pageSize);
}
