package com.lch.cloudfavorite.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class TokenUtil {
    private static final long TOKEN_DURATION = 30 * 60;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public void saveToken(String userId, String token) {
        String key = userId + "_user_token";
        stringRedisTemplate.boundValueOps(key).set(token, TOKEN_DURATION, TimeUnit.SECONDS);
    }


    public boolean isTokenValid(String userId, String token) {
        String key = userId + "_user_token";
        String value = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        Long expire = stringRedisTemplate.boundHashOps(key).getExpire();
        if (expire == null || expire <= 0) {
            return false;
        }

        if (!value.equals(token)) {
            return false;
        }

        return true;
    }
}
