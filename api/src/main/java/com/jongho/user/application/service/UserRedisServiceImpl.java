package com.jongho.user.application.service;

import com.jongho.user.application.repository.UserRedisRepository;
import com.jongho.user.domain.model.redis.CachedUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRedisServiceImpl {
    private final UserRedisRepository userRedisRepository;

    public CachedUserProfile getUserProfileByUserId(Long userId){
        return userRedisRepository.selectUserProfileByUserId(userId);
    }

    public void createUserProfileByUserId(Long userId, String nickname, String profileImage){
        userRedisRepository.createUserProfileByUserId(userId, nickname, profileImage);
    }
}
