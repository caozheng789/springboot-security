package per.cz.security.controller;


import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import per.cz.security.entity.Role;
import per.cz.security.entity.dto.RoleDto;
import per.cz.security.mapper.RoleMapper;
import per.cz.security.service.RoleService;

import java.util.List;

/**
 * 角色相关接口
 *
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleMapper roleDao;

	/**
	 * 保存角色
	 * @param roleDto
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('sys:role:add')")
	public void saveRole(@RequestBody RoleDto roleDto) {
		roleService.saveRole(roleDto);
	}

//	/**
//	 * 角色列表
//	 * @param request
//	 * @return
//	 */
//	@GetMapping
//	@PreAuthorize("hasAuthority('sys:role:query')")
//	public PageTableResponse listRoles(PageTableRequest request) {
//		return new PageTableHandler(new CountHandler() {
//
//			@Override
//			public int count(PageTableRequest request) {
//				return roleDao.count(request.getParams());
//			}
//		}, new ListHandler() {
//
//			@Override
//			public List<Role> list(PageTableRequest request) {
//				List<Role> list = roleDao.list(request.getParams(), request.getOffset(), request.getLimit());
//				return list;
//			}
//		}).handle(request);
//	}

	/**
	 * 根据id获取角色
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('sys:role:query')")
	public Role get(@PathVariable Long id) {
		return roleDao.getById(id);
	}

	/**
	 * 所有角色
	 * @return
	 */
	@GetMapping("/all")
	@PreAuthorize("hasAnyAuthority('sys:user:query','sys:role:query')")
	public List<Role> roles() {
		return roleDao.list(Maps.newHashMap(), null, null);
	}

	/**
	 * 根据用户id获取拥有的角色
	 * @param userId
	 * @return
	 */
	@GetMapping(params = "userId")
	@PreAuthorize("hasAnyAuthority('sys:user:query','sys:role:query')")
	public List<Role> roles(Long userId) {
		return roleDao.listByUserId(userId);
	}

	/**
	 * 删除角色
	 * @param id
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('sys:role:del')")
	public void delete(@PathVariable Long id) {
		roleService.deleteRole(id);
	}
}
