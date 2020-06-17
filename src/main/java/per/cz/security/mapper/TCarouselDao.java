package per.cz.security.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import per.cz.security.entity.TCarousel;


@Mapper
public interface TCarouselDao extends BaseMapper<TCarousel> {

    @Select("select * from t_carousel t where t.id = #{id}")
    TCarousel getById(Long id);

    @Delete("delete from t_carousel where id = #{id}")
    int delete(Long id);

    int update(TCarousel tCarousel);
    
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into t_carousel(status, sorting, create_time, update_time, img_url) values(#{status}, #{sorting}, #{createTime}, #{updateTime}, #{imgUrl})")
    int save(TCarousel tCarousel);
    
    int count(@Param("params") Map<String, Object> params);

    List<TCarousel> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
