package per.cz.security.service;

import per.cz.security.entity.ArticleInfo;
import per.cz.security.entity.PluginPage;
import per.cz.security.result.ResultData;

/**
 * Created by Administrator on 2020/5/24.
 */
public interface ArticleServiceI {


	ResultData getArticles(PluginPage<ArticleInfo> pluginPage);

	ResultData getArticleById(Long artId);

	ResultData getTopNRanks();

    ResultData pusArticle(String title, String data);

	ResultData putBlog(ArticleInfo article);
}
