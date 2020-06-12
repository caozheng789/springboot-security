package per.cz.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import per.cz.security.entity.Role;
import per.cz.security.entity.dto.RoleDto;
import per.cz.security.mapper.RoleMapper;
import per.cz.security.service.RoleService;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class RoleServiceImpl implements RoleService {


	@Autowired
	private RoleMapper roleMapper;

	@Override
	@Transactional
	public void saveRole(RoleDto roleDto) {
		Role role = roleDto;
		List<Long> permissionIds = roleDto.getPermissionIds();
		permissionIds.remove(0L);

		if (role.getId() != null) {// 修改
			updateRole(role, permissionIds);
		} else {// 新增
			saveRole(role, permissionIds);
		}
	}

	/**
	 * 新增角色{}
	 * @param role
	 * @param permissionIds
	 */
	private void saveRole(Role role, List<Long> permissionIds) {
		Role r = roleMapper.getRole(role.getName());
		if (r != null) {
			throw new IllegalArgumentException(role.getName() + "已存在");
		}

		roleMapper.save(role);
		if (!CollectionUtils.isEmpty(permissionIds)) {
			roleMapper.saveRolePermission(role.getId(), permissionIds);
		}
	}

	/**
	 * 修改角色{}
	 * @param role
	 * @param permissionIds
	 */
	private void updateRole(Role role, List<Long> permissionIds) {
		Role r = roleMapper.getRole(role.getName());
		if (r != null && r.getId() != role.getId()) {
			throw new IllegalArgumentException(role.getName() + "已存在");
		}

		roleMapper.update(role);

		roleMapper.deleteRolePermission(role.getId());
		if (!CollectionUtils.isEmpty(permissionIds)) {
			roleMapper.saveRolePermission(role.getId(), permissionIds);
		}
	}

	/**
	 * 删除角色id
	 * @param id
	 */
	@Override
	@Transactional
	public void deleteRole(Long id) {
		roleMapper.deleteRolePermission(id);
		roleMapper.deleteRoleUser(id);
		roleMapper.delete(id);
	}

}
