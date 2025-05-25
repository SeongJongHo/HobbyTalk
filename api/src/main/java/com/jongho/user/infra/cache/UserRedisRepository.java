package com.jongho.user.infra.cache;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import com.jongho.user.application.repository.IUserRedisRepository;
import com.jongho.user.domain.model.redis.CachedUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRedisRepository implements IUserRedisRepository {
    private final BaseRedisTemplate baseRedisTemplate;
    @Override
    public CachedUserProfile selectUserProfileByUserId(Long userId) {
        return baseRedisTemplate.get(RedisKeyGeneration.getUserProfileKey(userId),
            CachedUserProfile.class);
    }
    @Override
    public void createUserProfileByUserId(Long userId, String nickname, String profileImage) {
        baseRedisTemplate.set(RedisKeyGeneration.getUserProfileKey(userId),
            new CachedUserProfile(userId, nickname, profileImage));
    }
}
