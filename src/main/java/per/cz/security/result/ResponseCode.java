package per.cz.security.result;

/**
 *
 * Created by Administrator on 2020/4/3.
 */
public enum  ResponseCode {

	/** 成功 */
	SUCCESS("10001", "成功"),

	/** 操作失败 */
	ERROR("10002", "操作失败"),

	/** 未找到 */
	NOTFIND("10003","未找到"),

	/** 没有权限 */
	NOTPERMISSION("10004","没有权限");


	private ResponseCode(String value, String msg){
		this.val = value;
		this.msg = msg;
	}

	public String val() {
		return val;
	}

	public String msg() {
		return msg;
	}

	private String val;
	private String msg;

}
