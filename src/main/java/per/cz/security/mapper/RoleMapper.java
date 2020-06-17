package per.cz.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import per.cz.security.entity.Permission;
import per.cz.security.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/11 15:47
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_role(name, description, createTime, updateTime) values(#{name}, #{description}, now(),now())")
    int save(Role role);

    int count(@Param("params") Map<String, Object> params);

    List<Role> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset,
                    @Param("limit") Integer limit);

    @Select("select * from sys_role t where t.id = #{id}")
    Role getById(Long id);

    @Select("select * from sys_role t where t.name = #{name}")
    Role getRole(String name);

    @Update("update sys_role t set t.name = #{name}, t.description = #{description}, updateTime = now() where t.id = #{id}")
    int update(Role role);

    @Select("select * from sys_role r inner join sys_role_user ru on r.id = ru.roleId where ru.userId = #{userId}")
    List<Role> listByUserId(Long userId);

    @Delete("delete from sys_role_permission where roleId = #{roleId}")
    int deleteRolePermission(Long roleId);

    int saveRolePermission(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);

    @Delete("delete from sys_role where id = #{id}")
    int delete(Long id);

    @Delete("delete from sys_role_user where roleId = #{roleId}")
    int deleteRoleUser(Long roleId);

}
