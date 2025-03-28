package url.shortener.appliction.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableAutoConfiguration(exclude = {RedisRepositoriesAutoConfiguration.class})
//@EnableCaching
public class RedisConfig {

	public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("redis", 6379));
    }

    @Bean
    public StringRedisTemplate redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
    	 return new StringRedisTemplate(redisConnectionFactory);
    }
}