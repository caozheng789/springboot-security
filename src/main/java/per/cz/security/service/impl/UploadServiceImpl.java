package per.cz.security.service.impl;

import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import per.cz.security.entity.Area;
import per.cz.security.entity.Qiniu;
import per.cz.security.mapper.UploadMapper;
import per.cz.security.result.ResultData;
import per.cz.security.service.UploadServiceI;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 文件操作 七牛云
 * Created by Administrator on 2020/5/25.
 */
@Service
public class UploadServiceImpl implements UploadServiceI {

	@Autowired
	private UploadMapper uploadSqlMapper;

	@Override
	public ResultData uploadImg(MultipartFile file) {
		Qiniu qiniu = uploadSqlMapper.selectOne(null);

		if (null == qiniu){
			return ResultData.error("图片服务器异常~");
		}

		//图片服务器地址
		Configuration cfg = new Configuration(Region.region2());
		UploadManager uploadManager= new UploadManager(cfg);

		String fileKey = null;

		Auth auth = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
		String token = auth.uploadToken(qiniu.getBucket());
		try {
			String originalFilename = file.getOriginalFilename();
			// 文件后缀
			String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
			fileKey = UUID.randomUUID().toString() + suffix;
			Response response = uploadManager.put(file.getInputStream(), fileKey, token, null, null);

		} catch (IOException e) {
			return ResultData.error(e.getMessage());
		}
		return ResultData.success(qiniu.getDomain() + fileKey);
	}

	@Override
	public ResultData addQiNiuKey(Qiniu qiniu) {
		try {
			List<Qiniu> qinius = uploadSqlMapper.selectList(null);
			//设置地区
			String name = qiniu.getArea();
			String getflag = Area.getflag(name);

			qiniu.setArea(getflag);
			if (qinius.size() != 0){
				//修改
				String id = qinius.get(0).getId();
				qiniu.setId(id);
				uploadSqlMapper.updateById(qiniu);
			}else{
				//插入
				uploadSqlMapper.insert(qiniu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.error(e.getMessage());
		}
		return ResultData.success("更新成功 ~");
	}
}
