package per.cz.security.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class Permission extends BaseEntity<Long> implements Serializable {

	private Long parentId;
	private String title;
	private String css;
	private String href;
	private Integer type;
	private String permission;
	private Integer sort;

	private List<Permission> children;


}
