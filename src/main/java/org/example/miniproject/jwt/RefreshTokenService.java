package org.example.miniproject.jwt;

import lombok.RequiredArgsConstructor;
import org.example.miniproject.model.entity.AppUser;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate redis;
    // 7 days
    private static final Duration REFRESH_TTL = Duration.ofDays(3);
    private static final String PREFIX = "rt:";

    public String create(AppUser user) {
        String token = UUID.randomUUID().toString();
        String key = PREFIX + token;
        redis.opsForValue().set(key, user.getEmail(), REFRESH_TTL);
        return token;
    }

    public String verifyAndConsume(String token) {
        String key = PREFIX + token;
        String email = redis.opsForValue().get(key);
        if (email == null) throw new IllegalStateException("Invalid or expired refresh token");
//        redis.delete(key);
        return email;
    }


    //for log out
    public void revoke(String token) {
        if (token != null && !token.isBlank()) {
            redis.delete(PREFIX + token);
        }
    }
}
