package per.cz.security.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/4/3.
 */
@Data
public class ResultData implements Serializable {

	private String code;

	private String msg;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object data;

	public static ResultData success(Object data) {
		return resultData(ResponseCode.SUCCESS.val(), ResponseCode.SUCCESS.msg(), data);
	}

	public static ResultData success(Object data, String msg) {
		return resultData(ResponseCode.SUCCESS.val(), msg, data);
	}

	public static ResultData error(String msg) {
		return resultData(ResponseCode.ERROR.val(), msg, "");
	}

	public static ResultData notFind() {
		return resultData(ResponseCode.NOTFIND.val(), ResponseCode.NOTFIND.msg(), "");
	}

	public static ResultData notPermission() {
		return resultData(ResponseCode.NOTPERMISSION.val(), ResponseCode.NOTPERMISSION.msg(), "");
	}



	private static ResultData resultData(String code, String msg, Object data) {
		ResultData resultData = new ResultData();
		resultData.setCode(code);
		resultData.setMsg(msg);
		resultData.setData(data);
		return resultData;
	}



}
