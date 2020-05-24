package per.cz.security.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import per.cz.security.entity.LoginUser;
import per.cz.security.entity.Permission;
import per.cz.security.entity.UserDto;
import per.cz.security.mapper.PermissionMapper;
import per.cz.security.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;


/**
 * 认证用户信息
 *
 */
@Service
public class UserDetailsImpl implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	PermissionMapper permissionMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		System.out.println("当前登录用户名：" + username);

		//查询数据库
		UserDto userDtos = userMapper.getUserByUserName(username);
		if (null == userDtos){
			return null;
		}

		LoginUser loginUser = new LoginUser();
		BeanUtils.copyProperties(userDtos, loginUser);

		List<Permission> permissions = permissionMapper.listByUserId(userDtos.getId());
		loginUser.setPermissions(permissions);

		//查询权限
		//List<String> permission = getUserPermissions(userDtos);
		//将集合转成数组
//		String[] permissionArrays = new String[permission.size()];
//		permission.toArray(permissionArrays);



//		UserDetails details = User.withUsername(userDtos.getUsername())
//															.password(userDtos.getPassword())
//															.authorities(permissionArrays)
//															.build();
		return loginUser;
	}

//	private List<String> getUserPermissions(UserDto userDtos) {
//		List<Permission> permissions = permissionMapper.getPermissionByUserId(userDtos.getId());
//		List<String> permission = new ArrayList<>();
//
//		for (Permission p: permissions) {
//			if (!"".equals(p.getPermission())){
//				permission.add(p.getPermission());
//			}
//		}
//		return permission;
//	}
}
