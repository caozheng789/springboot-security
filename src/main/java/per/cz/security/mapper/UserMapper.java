package per.cz.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import per.cz.security.entity.UserDto;

/**
 * Created by Administrator on 2020/5/23.
 */
@Mapper
public interface UserMapper  extends BaseMapper<UserDto> {

	@Select("select * from sys_user where username = #{username}")
	UserDto getUserByUserName(@Param("username") String username);
}
