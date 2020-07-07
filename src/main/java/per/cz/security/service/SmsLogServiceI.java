package per.cz.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import per.cz.security.entity.SmsLog;
import per.cz.security.entity.SmsType;
import per.cz.security.result.ResultData;

import java.util.Map;

/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/30 16:20
 */
public interface SmsLogServiceI extends IService<SmsLog> {

    ResultData sendSms(SmsType smsType, String userId, String mobile, Map<String,String> params);

    boolean checkValidCode(String mobile, String code,SmsType smsType);
}
