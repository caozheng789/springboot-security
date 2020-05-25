package per.cz.security.controller;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import per.cz.security.entity.Qiniu;
import per.cz.security.result.ResultData;
import per.cz.security.service.UploadServiceI;

/**
 * Created by Administrator on 2020/5/25.
 */
@RestController
public class UploadController  {

	private static final String KEY = "VyrwpYIt0BA1i-pBFHDOkQ80x8zB0h-9FVU6Fqcm";
	private static final String SC = "YR5AWBDCzz_ja3t6PrABz9pl9Ieubehkc94meTi3";
	private static final String BN = "copyrightshop";

	@Autowired
	private UploadServiceI uploadService;

	@PostMapping("addQiNiuKey")
	public ResultData addQiNiuKey(Qiniu qiniu){
		return uploadService.addQiNiuKey(qiniu);
	}

	@GetMapping("upload")
	public String testKey(){

		String bucket = "copyrightshop";
		String key = "zheng";

		Auth auth = Auth.create(KEY, SC);
		String upToken = auth.uploadToken(bucket,key);
		System.out.println(upToken);
		return upToken;
	}

	@PostMapping("uploadImg")
	public String uploadImg(MultipartFile file){
		return uploadService.uploadImg(file);
	}

	/**
	 * 文件上传
	 * @return
	 */
	@GetMapping("uploadFile")
	public String uploadFile(){
		//构造一个带指定 Region 对象的配置类
		Configuration cfg = new Configuration(	Region.region2());
//...其他参数参考类注释

		UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
		String accessKey = KEY;
		String secretKey = SC;
		String bucket = BN;
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
		String localFilePath = "C:\\Users\\Administrator\\Desktop\\blog\\gf_time_line_layui\\gf_time_line_layui\\res\\img\\banner.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;

		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);

		try {
			Response response = uploadManager.put(localFilePath, key, upToken);
			//解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				//ignore
			}
		}
		return null;
	}

}
