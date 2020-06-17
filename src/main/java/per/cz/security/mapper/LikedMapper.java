package per.cz.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import per.cz.security.entity.ArtLike;

/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/16 11:00
 */
@Mapper
public interface LikedMapper extends BaseMapper<ArtLike>{
}
