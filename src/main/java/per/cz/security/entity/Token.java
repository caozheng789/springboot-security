package per.cz.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Restful方式登陆token
 */
@Data
@ToString
@AllArgsConstructor
public class Token implements Serializable {

	private static final long serialVersionUID = 6314027741784310221L;

	private String token;
	/** 登陆时间戳（毫秒） */
	private Long loginTime;


}
