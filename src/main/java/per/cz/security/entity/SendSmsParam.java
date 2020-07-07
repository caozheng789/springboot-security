/*
 * Copyright (c) 2018-2999 广州亚米信息科技有限公司 All rights reserved.
 *
 * https://www.gz-yami.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package per.cz.security.entity;

import lombok.Data;
import per.cz.security.util.PrincipalUtil;

import javax.validation.constraints.Pattern;

@Data
public class SendSmsParam {

	/**
	 * 手机号
	 */
	@Pattern(regexp= PrincipalUtil.MOBILE_REGEXP,message = "请输入正确的手机号")
	private String mobile;

}
