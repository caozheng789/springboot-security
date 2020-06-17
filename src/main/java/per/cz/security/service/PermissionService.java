package per.cz.security.service;


import per.cz.security.entity.Permission;

public interface PermissionService {

	void save(Permission permission);

	void update(Permission permission);

	void delete(Long id);
}
