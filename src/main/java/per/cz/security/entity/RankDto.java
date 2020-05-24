package per.cz.security.entity;


import lombok.Data;
import lombok.ToString;

/**
 * 排行榜
 */
@Data
@ToString
public class RankDto {


	/**
	 * 标题
	 */
	private String title;

	private Long score;

	private Long userId;

	private Long rank;



}
