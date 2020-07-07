package per.cz.security.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.cz.security.entity.ArticleInfo;
import per.cz.security.entity.PluginPage;
import per.cz.security.entity.TCarousel;
import per.cz.security.mapper.TCarouselDao;
import per.cz.security.result.ResultData;
import per.cz.security.service.TCarouselService;

import java.util.List;

/**
 * Created by Administrator on 2020/6/6.
 */
@Service("tCarouselDao")
public class TCarouselServiceImpl implements TCarouselService {

	@Autowired
	TCarouselDao tCarouselDao;

	@Override
	public void save(TCarousel tCarousel) {
		tCarouselDao.save(tCarousel);
	}

	@Override
	public TCarousel getById(Long id) {
		return tCarouselDao.getById(id);
	}

	@Override
	public void update(TCarousel tCarousel) {
		tCarouselDao.update(tCarousel);
	}

	@Override
	public void delete(Long id) {
		tCarouselDao.delete(id);
	}

	@Override
	public ResultData list(PluginPage<TCarousel> pluginPage) {
		List<TCarousel> list = null;
		IPage<TCarousel> lists;
		try {
			lists = new Page<>(pluginPage.getPage(), pluginPage.getLimit());//参数一是当前页，参数二是每页个数
			lists = tCarouselDao.selectPage(lists, null);
//			list = lists.getRecords();
//			if (null == list || list.size() == 0){
//				return ResultData.error("暂无数据~");
//			}
		} catch (Exception e) {
			return ResultData.error(e.getMessage());
		}
		return ResultData.success(lists);
	}


}
