package per.cz.security.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import per.cz.security.entity.vo.IdxVo;
import per.cz.security.result.ResponseCode;
import per.cz.security.result.ResultData;
import per.cz.security.service.impl.BaseElasticService;

/**
 * es 检索
 * Created by Administrator on 2020/6/7.
 */
@RestController
@RequestMapping("/es")
public class EsController {

	@Autowired
	private BaseElasticService baseElasticService;

	/**
	 * @Description 创建Elastic索引
	 * @param idxVo
	 * @return xyz.wongs.weathertop.base.message.response.ResponseResult
	 * @throws
	 * @date 2019/11/19 11:07
	 */
	@RequestMapping(value = "/createIndex",method = RequestMethod.POST)
	public ResultData createIndex(@RequestBody IdxVo idxVo){
		try {
			//索引不存在，再创建，否则不允许创建
			if(!baseElasticService.indexExist(idxVo.getIdxName())){
				String idxSql = JSONObject.toJSONString(idxVo.getIdxSql());
				//log.warn(" idxName={}, idxSql={}",idxVo.getIdxName(),idxSql);
				baseElasticService.createIndex(idxVo.getIdxName(),idxSql);
			} else{
				return ResultData.error("索引已经存在，不允许创建");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ResultData.error(e.getMessage());
		}
		return ResultData.success("success~");
	}


}
