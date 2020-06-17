package per.cz.security.config;

/**
 * Created by Administrator on 2020/5/24.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {

//	@Bean
//	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//		StringRedisTemplate redis = new StringRedisTemplate();
//
//		redis.setConnectionFactory(redisConnectionFactory);
//
//		// 设置redis的String/value的默认序列化方式
//		DefaultSerializer stringRedisSerializer = new DefaultSerializer();
//		redis.setKeySerializer(stringRedisSerializer);
//		redis.setValueSerializer(stringRedisSerializer);
//		redis.setHashKeySerializer(stringRedisSerializer);
//		redis.setHashValueSerializer(stringRedisSerializer);
//
//		redis.afterPropertiesSet();
//
//		return redis;
//	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean("redisTemplate")
	public RedisTemplate redisTemplate(@Lazy RedisConnectionFactory connectionFactory) {
		RedisTemplate redis = new RedisTemplate();
		//序列化
		GenericToStringSerializer<String> keySerializer = new GenericToStringSerializer<String>(String.class);
		redis.setKeySerializer(keySerializer);
		redis.setHashKeySerializer(keySerializer);

		GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
		redis.setValueSerializer(valueSerializer);
		redis.setHashValueSerializer(valueSerializer);
		redis.setConnectionFactory(connectionFactory);

		return redis;
	}
}