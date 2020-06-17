package per.cz.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import per.cz.security.entity.Permission;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2020/5/23
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

	@Select("select distinct p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId inner join sys_role_user ru on ru.roleId = rp.roleId where ru.userId = #{userId} order by p.sort")
	List<Permission> getPermissionByUserId(Integer userId);

	@Select("select distinct p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId inner join sys_role_user ru on ru.roleId = rp.roleId where ru.userId = #{userId} and p.parentId = 0 order by p.sort")
	List<Permission> listByUserId(Integer id);
}
