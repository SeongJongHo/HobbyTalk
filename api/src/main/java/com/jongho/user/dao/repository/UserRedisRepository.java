package com.jongho.user.dao.repository;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import com.jongho.user.domain.model.redis.CachedUserProfile;
import com.jongho.user.application.repository.IUserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRedisRepository implements IUserRedisRepository {
    private final BaseRedisTemplate baseRedisTemplate;
    @Override
    public CachedUserProfile selectUserProfileByUserId(Long userId) {
        return baseRedisTemplate.getData(RedisKeyGeneration.getUserProfileKey(userId), CachedUserProfile.class);
    }
    @Override
    public void createUserProfileByUserId(Long userId, String nickname, String profileImage) {
        baseRedisTemplate.setData(RedisKeyGeneration.getUserProfileKey(userId), new CachedUserProfile(userId, nickname, profileImage));
    }
}
