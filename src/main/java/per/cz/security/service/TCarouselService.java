package per.cz.security.service;

import per.cz.security.entity.PluginPage;
import per.cz.security.entity.TCarousel;
import per.cz.security.result.ResultData;


/**
 * 轮播图管理
 * Created by Administrator on 2020/6/6.
 */
public interface TCarouselService {
	void save(TCarousel tCarousel);

	TCarousel getById(Long id);

	void update(TCarousel tCarousel);

	void delete(Long id);

	ResultData list(PluginPage<TCarousel> pluginPage);

}
