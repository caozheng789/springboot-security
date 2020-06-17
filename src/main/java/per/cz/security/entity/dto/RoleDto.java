package per.cz.security.entity.dto;


import lombok.Data;
import per.cz.security.entity.Role;

import java.util.List;

@Data
public class RoleDto extends Role {

	private static final long serialVersionUID = 4218495592167610193L;

	private List<Long> permissionIds;


}
