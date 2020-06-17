package per.cz.security.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/8 10:13
 */

@Data
@Configuration
public class ElasticSearchConfig {

    //ES集群名,默认值
    private String clusterName;

    //ES集群中节点的域名或IP
    @Value("${elastic-search.hostName}")
    private String hostName;

    //ES连接端口号
    @Value("${elastic-search.port}")
    private Integer port;

    //ES查询的索引名称
    private String indices;

    //ES查询的Types
    private String types;
}
