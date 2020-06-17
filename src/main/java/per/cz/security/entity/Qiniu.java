package per.cz.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/5/25.
 */
@Data
@TableName("t_qiniu")
public class Qiniu extends BaseEntity<Integer> implements Serializable {

	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 域名
	 */
	@TableField("domain")
	private String domain;

	/**
	 * 从下面这个地址中获取accessKey和secretKey
	 * https://portal.qiniu.com/user/key
	 */
	@TableField("access_key")
	private String accessKey;

	@TableField("secret_key")
	private String secretKey;

	/**
	 * 存储空间名
	 */

	private String bucket;

	/**
	 * 地区
	 */
	private String area;



}
