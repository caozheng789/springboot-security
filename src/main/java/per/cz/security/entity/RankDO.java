package per.cz.security.entity;

/**
 * Created by Administrator on 2020/5/24.
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
public class RankDO implements Serializable {
	private static final long serialVersionUID = 4804922606006935590L;

	/**
	 * 排名
	 */
	private Long rank;

	/**
	 * 积分
	 */
	private Long score;

	/**
	 * 用户id
	 */
	private Long userId;

}
