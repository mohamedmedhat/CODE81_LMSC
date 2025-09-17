package mohamed.code81.lms.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;

    public void storeStringTemporary(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value, 10, TimeUnit.MINUTES);
    }

    public String getTemporary(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void deleteTemporaryPassword(String email) {
        stringRedisTemplate.delete(email);
    }
}
