package per.cz.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import per.cz.security.entity.ArticleInfo;
import per.cz.security.entity.PluginPage;
import per.cz.security.result.ResultData;

/**
 * Created by Administrator on 2020/5/24.
 */
@Mapper
public interface ArticleMapper extends BaseMapper<ArticleInfo>{


	ResultData getArticles(PluginPage<ArticleInfo> pluginPage);
}
