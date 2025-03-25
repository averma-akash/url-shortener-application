package url.shortener.appliction.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	@Autowired
    private StringRedisTemplate redisTemplate;

    private static final long CACHE_EXPIRATION = 60 * 60 * 24; // 24hrs

    public void cacheUrl(String shortUrl, String longUrl) {
        redisTemplate.opsForValue().set(shortUrl, longUrl, CACHE_EXPIRATION, TimeUnit.SECONDS);
    }

    public String getCachedUrl(String shortUrl) {
        return redisTemplate.opsForValue().get(shortUrl);
    }

}
