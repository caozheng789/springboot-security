package per.cz.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.cz.security.entity.ArticleInfo;
import per.cz.security.entity.PluginPage;
import per.cz.security.result.ResultData;
import per.cz.security.service.ArticleServiceI;


/**
 * 文章
 * Created by Administrator on 2020/5/24.
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("art")
public class ArticleController {


	@Autowired
	private ArticleServiceI artService;

	/**
	 * 获取所有
	 * @return
	 */
	@RequestMapping(value = "getArticles", method = RequestMethod.POST)
	public ResultData getArticles(@RequestBody PluginPage<ArticleInfo> pluginPage){
		return artService.getArticles(pluginPage);
	}

	/**
	 * 根据id获取
	 * @return
	 */
	@GetMapping(value = "getArticleById")
	public ResultData getArticleById(Long artId){
		return artService.getArticleById(artId);
	}


	/**
	 * 获取排行榜数据
	 * @return
	 */
	@GetMapping(value = "getTopNRanks")
	public ResultData getTopNRanks(){
		return artService.getTopNRanks();
	}


	/**
	 * 发布博客
	 * @return
	 */
	@PostMapping("putBlog")
	public ResultData putBlog(ArticleInfo article){
		return artService.putBlog(article);
	}


	@PutMapping
	public ResultData pusArticle(String title, String data){
		return artService.pusArticle(title,data);
	}



}
