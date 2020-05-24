package per.cz.security.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 类说明:分页参数拼接
 *
 * @author : zhaotianliang
 * @date : 2020-04-05 23:01
 **/
@Setter
@Getter
public class PluginPage<T> {
  private Integer pageNum = 1;
  private Integer pageSize = 10;
  private String orderBy;
  private Map<String,Object> queryMap;
  private T t;
}