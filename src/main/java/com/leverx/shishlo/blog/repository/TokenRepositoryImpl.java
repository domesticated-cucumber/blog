package com.leverx.shishlo.blog.repository;

import com.leverx.shishlo.blog.exception.BlogException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import java.util.Optional;
import static java.lang.String.format;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private static final Long TTL = 86400L; // 24 hours

    private final Jedis jedisTemplate;

    @Override
    public void save(Long userId, String token) {
        var key = getKey(userId);
        jedisTemplate.setex(key, TTL, token);
    }

    @Override
    public Long checkTTL(String token) {
        var keys = jedisTemplate.keys("token:*");
        for (var key : keys) {
            var tokenFromRedis = jedisTemplate.get(key);
            if (token.equals(tokenFromRedis)) {
                return jedisTemplate.ttl(key);
            }
        }
        throw new BlogException("Token wasn't found.");
    }

    @Override
    public Optional<Long> check(String token) {
        var keys = jedisTemplate.keys("token:*");
        for (var key : keys) {
            var tokenFromRedis = jedisTemplate.get(key);
            if (token.equals(tokenFromRedis)) {
                return Optional.of(Long.parseLong(key.split(":")[1]));
            }
        }
        return Optional.empty();
    }

    private String getKey(Long userId) {
        return format("token:%d", userId);
    }
}
