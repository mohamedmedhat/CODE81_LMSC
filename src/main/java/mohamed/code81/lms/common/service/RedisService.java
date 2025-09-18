/*
 * Copyright (c) 2025 Mohamed Medhat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy at: http://www.apache.org/licenses/LICENSE-2.0
 */

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
