/*
 * Copyright (c) 2018-2999 广州亚米信息科技有限公司 All rights reserved.
 *
 * https://www.gz-yami.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package per.cz.security.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.cz.security.entity.SendSmsParam;
import per.cz.security.entity.SmsType;
import per.cz.security.entity.UserDto;
import per.cz.security.result.ResultData;
import per.cz.security.service.SmsLogServiceI;
import per.cz.security.service.UserServiceI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;


/**
 * 注册
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserRegisterController {

	private final UserServiceI userService;

	private final SmsLogServiceI smsLogService;
//
//	private final LoginAuthSuccessHandler loginAuthSuccessHandler;

	private final PasswordEncoder passwordEncoder;


	public static final String CHECK_REGISTER_SMS_FLAG = "checkRegisterSmsFlag";

	public static final String CHECK_UPDATE_PWD_SMS_FLAG = "updatePwdSmsFlag";

	public static final String CHECK_UPDATE_PHONE_SMS_FLAG = "updatePwdSmsFlag";

	/**
	 * 发送注册验证码
	 * @param sendSmsParam
	 * @return
	 */
	@PutMapping("/sendRegisterSms")
	public ResultData register(@Valid @RequestBody SendSmsParam sendSmsParam) {
		if (userService.count(new LambdaQueryWrapper<UserDto>().eq(UserDto::getPhone, sendSmsParam.getMobile())) > 0) {
			return ResultData.error("该手机号已注册，无法重新注册");
		}
		// 每个手机号每分钟只能发十个注册的验证码，免得接口被利用
		return smsLogService.sendSms(SmsType.REGISTER, sendSmsParam.getMobile(), sendSmsParam.getMobile(), Maps.newHashMap());
	}

//	@PutMapping("/checkRegisterSms")
//	@ApiOperation(value="校验验证码", notes="校验验证码返回校验成功的标识")
//	public ResponseEntity<String> register(@Valid @RequestBody CheckRegisterSmsParam checkRegisterSmsParam) {
//		// 每个ip每分钟只能发十个注册的验证码，免得接口被利用
//		if (!smsLogService.checkValidCode(checkRegisterSmsParam.getMobile(), checkRegisterSmsParam.getValidCode(), SmsType.REGISTER)){
//			throw new YamiShopBindException("验证码有误或已过期");
//		}
//		String checkRegisterSmsFlag = IdUtil.simpleUUID();
//		RedisUtil.set(CHECK_REGISTER_SMS_FLAG + checkRegisterSmsFlag, checkRegisterSmsParam.getMobile(), 600);
//		return ResponseEntity.ok(checkRegisterSmsFlag);
//	}
//
//	/**
//	 * 校验手机验证码
//	 * @param checkRegisterSmsParam
//	 * @return 校验手机验证码返回校验成功的标识
//	 */
//	@PutMapping("/checkPhoneSms")
//	public ResponseEntity<String> phone(@Valid @RequestBody CheckRegisterSmsParam checkRegisterSmsParam) {
//		// 每个ip每分钟只能发十个注册的验证码，免得接口被利用
//		if (!smsLogService.checkValidCode(checkRegisterSmsParam.getMobile(), checkRegisterSmsParam.getValidCode(), SmsType.UPDATE_PHONE)){
//			throw new YamiShopBindException("验证码有误或已过期");
//		}
//		String checkPhoneSmsFlag = IdUtil.simpleUUID();
//		RedisUtil.set(CHECK_UPDATE_PHONE_SMS_FLAG + checkPhoneSmsFlag, checkRegisterSmsParam.getMobile(), 600);
//		return ResponseEntity.ok(checkPhoneSmsFlag);
//	}
//
//	@PutMapping("/sendBindSms")
//	@ApiOperation(value="发送绑定验证码", notes="发送绑定验证码")
//	public ResponseEntity<Void> bindSms(@Valid @RequestBody SendSmsParam sendSmsParam) {
//		// 每个手机号每分钟只能发十个注册的验证码，免得接口被利用
//		smsLogService.sendSms(SmsType.VALID, sendSmsParam.getMobile(), sendSmsParam.getMobile(), Maps.newHashMap());
//		return ResponseEntity.ok().build();
//	}
//
//	@PutMapping("/registerOrBindUser")
//	@ApiOperation(value="注册或绑定手机号", notes="用户注册或绑定手机号接口")
//	public ResponseEntity<Void> register(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody UserRegisterParam userRegisterParam) {
//
//		String mobile = userRegisterParam.getMobile();
//		AppConnect appConnect = null;
//		User user = null;
//		String bizUserId = null;
//
//		// 正在进行注册，通过验证码校验
//		if (Objects.equals(userRegisterParam.getRegisterOrBind(), 1)) {
//
//			// 看看有没有校验验证码成功的标识
//			userService.validate(userRegisterParam, CHECK_REGISTER_SMS_FLAG + userRegisterParam.getCheckRegisterSmsFlag());
//			// 正在进行申请注册
//			if (userService.count(new LambdaQueryWrapper<User>().eq(User::getUserMobile,userRegisterParam.getMobile())) > 0) {
//				throw new YamiShopBindException("手机号已存在，无法注册");
//			}
//		}
//		// 小程序注册/绑定手机号
//		else {
//			YamiUser yamiUser =  SecurityUtils.getUser();
//			appConnect = appConnectService.getByBizUserId(yamiUser.getBizUserId(), yamiUser.getAppType());
//			bizUserId = yamiUser.getBizUserId();
//			// 通过微信手机号校验
//			if (Objects.equals(2, userRegisterParam.getValidateType())) {
//				try {
//					WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxConfig.getWxMaService().getUserService().getPhoneNoInfo(yamiUser.getSessionKey(), userRegisterParam.getEncryptedData(), userRegisterParam.getIvStr());
//					mobile = wxMaPhoneNumberInfo.getPhoneNumber();
//
//				} catch (Exception e) {
//					throw new YamiShopBindException("授权失败，请重新授权");
//				}
//				if (StrUtil.isBlank(mobile)) {
//					throw new YamiShopBindException("无法获取用户手机号信息");
//				}
//				user = yamiUserDetailsService.loadUserByMobileOrUserName(mobile, 0);
//			}
//			// 通过账号密码校验
//			else if (Objects.equals(3, userRegisterParam.getValidateType())) {
//				user = yamiUserDetailsService.loadUserByMobileOrUserName(mobile, 0);
//				if (user == null) {
//					throw new YamiShopBindException("账号或密码不正确");
//				}
//				String encodedPassword = user.getLoginPassword();
//				String rawPassword = userRegisterParam.getPassword();
//				// 密码不正确
//				if (StrUtil.isBlank(encodedPassword) || !passwordEncoder.matches(rawPassword,encodedPassword)){
//					throw new YamiShopBindException("账号或密码不正确");
//				}
//			}
//			// 通过验证码校验
//			else {
//				if (!smsLogService.checkValidCode(userRegisterParam.getMobile(), userRegisterParam.getValidCode(), SmsType.VALID)){
//					throw new YamiShopBindException("验证码有误或已过期");
//				}
//			}
//		}
//
//		Date now = new Date();
//
//		// 尝试用手机号获取用户信息
//		if (user == null && StrUtil.isNotBlank(mobile)) {
//			user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserMobile,mobile));
//		}
//
//		// 新建用户
//		if (user == null) {
//			user = new User();
//			if (StrUtil.isBlank(userRegisterParam.getUserName())) {
//				userRegisterParam.setUserName(mobile);
//			}
//
//			// 如果有用户名,就判断用户名格式是否正确
//			if (!PrincipalUtil.isUserName(userRegisterParam.getUserName())) {
//				throw new YamiShopBindException("用户名应由4-16位数字字母下划线组成");
//			}
//
//			user.setModifyTime(now);
//			user.setUserRegtime(now);
//			user.setUserRegip(IPHelper.getIpAddr());
//			user.setStatus(1);
//
//			user.setPic(userRegisterParam.getImg());
//			user.setUserMobile(mobile);
//			user.setUserName(userRegisterParam.getUserName());
//			if (StrUtil.isNotBlank(userRegisterParam.getPassword())) {
//				user.setLoginPassword(passwordEncoder.encode(userRegisterParam.getPassword()));
//			}
//			// 用户名就是默认的昵称
//			user.setNickName(StrUtil.isBlank(userRegisterParam.getNickName())? userRegisterParam.getUserName(): userRegisterParam.getNickName());
//		}
//
//
//		appConnectService.registerOrBindUser(user, appConnect, userRegisterParam.getAppType());
//
//
//		ImPackage imPackage = new ImPackage();
//		imPackage.setIdentifier(user.getUserId());
////        imPackage.setFaceUrl(register.getHeadPictureUrl());
//		boolean flag = createImAccount(imPackage);
//
//		if (!flag) {
//			System.out.println("--腾讯im注册失败--");
//		}else {
//			System.out.println("--腾讯im注册成功--");
//		}
//
//		//进行授权登录
//		UserDetails userDetails = yamiUserDetailsService.getYamiUser(userRegisterParam.getAppType(),user, bizUserId);
//		AuthenticationToken authenticationToken = new AuthenticationToken();
//		authenticationToken.setPrincipal(user.getUserMobile());
//		authenticationToken.setCredentials(user.getLoginPassword());
//		authenticationToken.setPrincipal(userDetails.getUsername());
//		authenticationToken.setDetails(userDetails);
//		authenticationToken.setAuthenticated(true);
//		loginAuthSuccessHandler.onAuthenticationSuccess(request,response,authenticationToken);
//
//		return ResponseEntity.ok().build();
//	}
//
//
//	/**
//	 * 修改密码
//	 * @param userRegisterParam
//	 * @return
//	 */
//	@PutMapping("/updatePwd")
//	public ResponseEntity<Void> updatePwd(@Valid @RequestBody UserRegisterParam userRegisterParam) {
//		User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserMobile, userRegisterParam.getMobile()));
//		if (user == null) {
//			throw new YamiShopBindException("无法获取用户信息");
//		}
//		// 看看有没有校验验证码成功的标识
//		userService.validate(userRegisterParam, CHECK_UPDATE_PWD_SMS_FLAG + userRegisterParam.getCheckRegisterSmsFlag());
//		if (StrUtil.isBlank(userRegisterParam.getPassword())) {
//			throw new YamiShopBindException("新密码不能为空");
//		}
//		if (StrUtil.equals(passwordEncoder.encode(userRegisterParam.getPassword()),user.getLoginPassword())) {
//			throw new YamiShopBindException("新密码不能与原密码相同!");
//		}
//		user.setModifyTime(new Date());
//		user.setLoginPassword(passwordEncoder.encode(userRegisterParam.getPassword()));
//		userService.updateById(user);
//		return ResponseEntity.ok().build();
//	}
}
