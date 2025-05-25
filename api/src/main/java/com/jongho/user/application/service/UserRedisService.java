package com.jongho.user.application.service;

import com.jongho.user.application.repository.IUserRedisRepository;
import com.jongho.user.domain.model.redis.CachedUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRedisService {
    private final IUserRedisRepository IUserRedisRepository;

    public CachedUserProfile getUserProfileByUserId(Long userId){
        return IUserRedisRepository.selectUserProfileByUserId(userId);
    }

    public void createUserProfileByUserId(Long userId, String nickname, String profileImage){
        IUserRedisRepository.createUserProfileByUserId(userId, nickname, profileImage);
    }
}
