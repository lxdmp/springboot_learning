package com.lxdmp.springboottest.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Configuration
public class RedisConfig
{
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory)
	{
		RedisTemplate<String, Object> template = new RedisTemplate();
		template.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer); // key采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer); // hash的key也采用String的序列化方式
		template.setValueSerializer(jackson2JsonRedisSerializer); // value序列化方式采用jackson
		template.setHashValueSerializer(jackson2JsonRedisSerializer); // hash的value序列化方式采用jackson
		template.afterPropertiesSet();
		return template;
	}
}

