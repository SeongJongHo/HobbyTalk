package com.jongho.user.domain.repository;

import com.jongho.user.domain.model.redis.CachedUserProfile;

public interface UserRedisRepository {
    public CachedUserProfile selectUserProfileByUserId(Long userId);
    public void createUserProfileByUserId(Long userId, String nickname, String profileImage);
}
