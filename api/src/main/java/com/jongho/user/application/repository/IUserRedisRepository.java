package com.jongho.user.application.repository;

import com.jongho.user.domain.model.redis.CachedUserProfile;

public interface IUserRedisRepository {
    public CachedUserProfile selectUserProfileByUserId(Long userId);
    public void createUserProfileByUserId(Long userId, String nickname, String profileImage);
}
