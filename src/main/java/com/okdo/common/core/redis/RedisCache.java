package com.okdo.common.core.redis;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;


@Component
@AllArgsConstructor
public class RedisCache {

    final RedisTemplate redisTemplate;

    public <T> void string(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> void string(final String key, final T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public String getString(final String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void delString(final String key) {
        redisTemplate.delete(key);
    }
}
