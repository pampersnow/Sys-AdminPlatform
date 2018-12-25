package com.sys.pro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter Redis 配置
 * @see 集群下启动session共享，需打开@EnableRedisHttpSession<br>
 *      单机下不需要
 * @return 返回值
 * @throws 异常类及抛出条件
 */
@Configuration
//@EnableRedisHttpSession
public class RedisConfig {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean("redisTemplate")
	public RedisTemplate redisTemplate(@Lazy RedisConnectionFactory connectionFactory) throws Exception{

		RedisTemplate redis = new RedisTemplate<>();
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
