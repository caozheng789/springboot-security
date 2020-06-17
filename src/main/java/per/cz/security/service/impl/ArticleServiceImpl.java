package per.cz.security.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import per.cz.security.entity.*;
import per.cz.security.mapper.ArticleMapper;
import per.cz.security.mapper.MenuMapper;
import per.cz.security.result.ResultData;
import per.cz.security.service.ArticleServiceI;
import per.cz.security.util.BeanUtil;
import per.cz.security.util.HtmlToText;
import per.cz.security.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/5/24.
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleServiceI {

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public ResultData getArticles(PluginPage<ArticleInfo> pluginPage) {

		log.info("## 1. 获取所有文章 getArticles start ##");
		List<ArticleInfo> list = null;
		try {
			IPage<ArticleInfo> userPage = new Page<>(pluginPage.getPageNum(), pluginPage.getPageSize());//参数一是当前页，参数二是每页个数
			userPage = articleMapper.selectPage(userPage, null);
			list = userPage.getRecords();
			if (list.size() != 0){
				for ( ArticleInfo art : list) {
					//菜单名回填
					//getMenuName(art);

					//把富文本中的img拿出来做首页展示
					List<String> data = HtmlToText.getImageSrc(art.getData());
					if(data!=null && !data.isEmpty()){
						//取第一张即可
						art.setThematicUrl(data.get(0).toString());
					}
					//去除富文本标签，只要文本内容
					String text = HtmlToText.StripHT(art.getData());
					art.setData(text);
				}
			}
		} catch (Exception e) {
			log.error("@@ 1. 获取所有文章 getArticles err @@",e);
			return ResultData.error(e.getMessage());
		}
		log.info("## 2. 获取所有文章 getArtices end ##");
		return ResultData.success(list);
	}

	//回填菜单名
	private void getMenuName(ArticleInfo art) {
		Menu menu = menuMapper.selectById(art.getArtFirstMenu());
		art.setArtFirstMenu(menu.getMenuText());
		Menu menu1 = menuMapper.selectById(art.getArtSubMenu());
		art.setArtSubMenu(menu1.getMenuText());
	}


	@Autowired
	private RankListComponent rankListComponent;

	@Override
	public ResultData getArticleById(Long artId) {
		log.info("## 1. 根据id查询推荐文章 getArticleById start ##");
		ArticleInfo articleInfo = null;
        try {
					articleInfo = articleMapper.selectById(artId);
					if (null != articleInfo){
						//getMenuName(articleInfo);
						//将被请求的文章放入redis,排行榜
						RankDO rank = rankListComponent.getRank(artId);
						if (rank.getRank() == -1){
							rankListComponent.updateRank(artId, 1L);
						}else {
							rankListComponent.updateRank(artId, Math.abs(rank.getScore())+1);
						}

					}
				} catch (Exception e) {
            log.error("@@ 1. 根据id查询推荐文章 getArticleById err @@",e);
					return ResultData.error(e.getMessage());
        }
        log.info("## 2. 根据id查询推荐文章 getArticleById end ##");
		return ResultData.success(articleInfo);
	}

	@Override
	public ResultData getTopNRanks() {
		List<RankDO> topNRanks = rankListComponent.getTopNRanks(10);

		if (null == topNRanks){
			return ResultData.error("暂无热搜~");
		}

		List<RankDto> dtos = new ArrayList<>();
		for (RankDO r:topNRanks) {
			RankDto rankDto = new RankDto();
			BeanUtil.copyNotNullBean(r,rankDto);
			ArticleInfo articleInfo = articleMapper.selectById(r.getUserId());
			if (null !=  articleInfo){
				rankDto.setTitle(articleInfo.getTitle());
				//取出绝对值
				rankDto.setScore(Math.abs(rankDto.getScore()));
				dtos.add(rankDto);
			}
		}
		return ResultData.success(dtos);
	}


	/**
	 * 发布博客
	 * @param article
	 * @return
	 */
	@Override
	public ResultData putBlog(ArticleInfo article) {
		LoginUser loginUser = UserUtil.getLoginUser();
		if (null == loginUser ){
			return ResultData.success("请先登录~");
		}
		article.setUserName(loginUser.getId()+"");
		Long id;
		try {
			int insert = articleMapper.insert(article);
			id = article.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.error(e.getMessage());
		}
		return ResultData.success(id+"");
	}


}
