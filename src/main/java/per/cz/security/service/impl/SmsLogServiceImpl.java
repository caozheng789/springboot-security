/*
 * Copyright (c) 2018-2999 广州亚米信息科技有限公司 All rights reserved.
 *
 * https://www.gz-yami.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package per.cz.security.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.cz.security.entity.SmsLog;
import per.cz.security.entity.SmsType;
import per.cz.security.mapper.SmsLogMapper;
import per.cz.security.result.ResultData;
import per.cz.security.service.SmsLogServiceI;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author lgh on 2018/11/29.
 */
@Service
@Slf4j
@AllArgsConstructor
public class SmsLogServiceImpl extends ServiceImpl<SmsLogMapper, SmsLog> implements SmsLogServiceI {

    private SmsLogMapper smsLogMapper;

    private ShopConfig shopConfig;

    /**
     * 产品域名,开发者无需替换
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    /**
     * 产品RegionId,开发者无需替换
     */
    private static final String REGION_ID = "cn-hangzhou";

    /**
     * 产品version,开发者无需替换
     */
    private static final String VERSION = "2017-05-25";

    /**
     * 产品Action,开发者无需替换
     */
    private static final String ACTION = "SendSms";

    /**
     * 当天最大验证码短信发送量
     */
    private static final int TODAY_MAX_SEND_VALID_SMS_NUMBER = 10;

    /**
     * 一段时间内短信验证码的最大验证次数
     */
    private static final int TIMES_CHECK_VALID_CODE_NUM = 10;

    /**
     * 短信验证码的前缀
     */
    private static final String CHECK_VALID_CODE_NUM_PREFIX = "checkValidCodeNum_";

    /**
     * 短信列表的大小/列表的索引
     */
    private static final Integer INDEX = 0;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultData sendSms(SmsType smsType, String userId, String mobile, Map<String, String> params) {



        SmsLog smsLog = new SmsLog();

        List<SmsLog> smsLogList = smsLogMapper.selectList(new LambdaQueryWrapper<SmsLog>()
                .gt(SmsLog::getRecDate, DateUtil.beginOfDay(new Date()))
                .lt(SmsLog::getRecDate, DateUtil.endOfDay(new Date()))
                .eq(SmsLog::getUserId, userId)
                .eq(SmsLog::getType, smsType.value())
                .orderByDesc(SmsLog::getRecDate)
        );
        if (smsLogList.size() >= TODAY_MAX_SEND_VALID_SMS_NUMBER) {
            return ResultData.error("今日发送短信验证码次数已达到上限");
        }

        if (smsLogList.size() > INDEX){
            SmsLog smsLogLast = smsLogList.get(INDEX);
            long currentTimeMillis = System.currentTimeMillis();
            long timeDb = DateUtil.offsetSecond(smsLogLast.getRecDate(), 60).getTime();
            if (currentTimeMillis < timeDb){
                return ResultData.error("一分钟内只能发送一次验证码");
            }
        }
        // 将上一条验证码失效
        smsLogMapper.invalidSmsByMobileAndType(mobile, smsType.value());

        String code = RandomUtil.randomNumbers(6);

        int appid = 1400233127;
        String appkey = "6aac7712ca79ddc6ed5f2e8df940da84";
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        // 需要发送短信的手机号码
        String[] phoneNumbers = {mobile};
        SmsSingleSenderResult result1 = null;
        try {
            result1 = ssender.send(0, "86", phoneNumbers[0],
                    "验证码为："+ code +"，您正在注册成为TimeLine平台会员，感谢您的支持！", "", "");
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result1);

        smsLog.setType(smsType.value());
        smsLog.setMobileCode(params.get("code"));
        smsLog.setRecDate(new Date());
        smsLog.setStatus(1);
        smsLog.setUserId(userId);
        smsLog.setUserPhone(mobile);
        smsLog.setContent(formatContent(smsType, params));
        smsLogMapper.insert(smsLog);

        this.sendSms(mobile, smsType.getTemplateCode(), params);
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
//    public boolean checkValidCode(String mobile, String code, SmsType smsType) {
//        long checkValidCodeNum = RedisUtil.incr(CHECK_VALID_CODE_NUM_PREFIX + mobile, 1);
//        if (checkValidCodeNum == 0) {
//            // 半小时后失效
//            RedisUtil.expire(CHECK_VALID_CODE_NUM_PREFIX + mobile, 1800);
//        }
//        if (checkValidCodeNum >= TIMES_CHECK_VALID_CODE_NUM) {
//            throw new YamiShopBindException("验证码校验过频繁，请稍后再试");
//        }
//        SmsLog sms = new SmsLog();
//        sms.setUserPhone(mobile);
//        sms.setMobileCode(code);
//        sms.setStatus(1);
//        sms.setType(smsType.value());
//
//        SmsLog dbSms = smsLogMapper.selectOne(new LambdaQueryWrapper<SmsLog>()
//                .eq(SmsLog::getUserPhone, mobile)
//                .eq(SmsLog::getMobileCode, code)
//                .eq(SmsLog::getStatus, 1)
//                .eq(SmsLog::getType, smsType.value()));
//        // 没有找到当前的验证码
//        if (dbSms == null) {
//            RedisUtil.incr(CHECK_VALID_CODE_NUM_PREFIX + mobile, 1);
//            return false;
//        }
//        RedisUtil.del(CHECK_VALID_CODE_NUM_PREFIX + mobile);
//        // 标记为失效状态
//        dbSms.setStatus(0);
////        smsLogMapper.updateById(dbSms);
//        // 验证码已过期
//        DateTime offsetMinute = DateUtil.offsetMinute(dbSms.getRecDate(), 5);
//        if (offsetMinute.getTime() < System.currentTimeMillis()) {
//            RedisUtil.incr(CHECK_VALID_CODE_NUM_PREFIX + mobile, 1);
//            return false;
//        }
//
//        return true;
//    }
//
//    private void sendSms(String mobile, String templateCode, Map<String, String> params) {
//        ALiDaYu aLiDaYu = shopConfig.getALiDaYu();
//
//        //初始化acsClient,暂不支持region化
//        IClientProfile profile = DefaultProfile.getProfile(REGION_ID, aLiDaYu.getAccessKeyId(), aLiDaYu.getAccessKeySecret());
//        IAcsClient acsClient = new DefaultAcsClient(profile);
//
//        //组装请求对象-具体描述见控制台-文档部分内容
//
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain(DOMAIN);
//        request.setSysVersion(VERSION);
//        request.setSysAction(ACTION);
//        request.putQueryParameter("RegionId", REGION_ID);
//        //必填:待发送手机号
//        request.putQueryParameter("PhoneNumbers", mobile);
//        //必填:短信签名-可在短信控制台中找到
//        request.putQueryParameter("SignName", aLiDaYu.getSignName());
//        //必填:短信模板-可在短信控制台中找到
//        request.putQueryParameter("TemplateCode", templateCode);
//        request.putQueryParameter("TemplateParam", Json.toJsonString(params));
//
//        try {
//            CommonResponse response = acsClient.getCommonResponse(request);
//            System.out.println(response.getData());
//        } catch (ClientException e) {
//            throw new YamiShopBindException("发送短信失败，请稍后再试:" + e.getMessage());
//        }
//    }
//
//    private String formatContent(SmsType smsType, Map<String, String> params) {
//        if (CollectionUtil.isEmpty(params)) {
//            return smsType.getContent();
//        }
//        String content = smsType.getContent();
//        for (Entry<String, String> element : params.entrySet()) {
//            content = content.replace("${" + element.getKey() + "}", element.getValue());
//        }
//        return content;
//    }
}
