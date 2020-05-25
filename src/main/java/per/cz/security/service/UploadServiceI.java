package per.cz.security.service;

import org.springframework.web.multipart.MultipartFile;
import per.cz.security.entity.Qiniu;
import per.cz.security.result.ResultData;

/**
 * Created by Administrator on 2020/5/25.
 */
public interface UploadServiceI {

	String uploadImg(MultipartFile file);

	/**
	 * 添加七牛云服务器信息
	 * @param qiniu
	 * @return
	 */
	ResultData addQiNiuKey(Qiniu qiniu);
}
