package per.cz.security.entity.bean;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import per.cz.security.config.ElasticSearchConfig;

/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/8 10:14
 */
@Configuration
public class ElasticsearchBean {

    private final ElasticSearchConfig esConfig;

    @Autowired
    public ElasticsearchBean(ElasticSearchConfig esConfig) {
        this.esConfig = esConfig;
    }

    /**
     * @return 封装 RestClient
     */
    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient(){
        String hostName = esConfig.getHostName();
        System.out.println(hostName);
        return new RestHighLevelClient(RestClient.builder(new HttpHost(esConfig.getHostName(), esConfig.getPort(), "http")));
    }
}
