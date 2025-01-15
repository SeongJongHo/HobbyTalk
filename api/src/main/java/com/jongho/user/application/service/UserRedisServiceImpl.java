package com.jongho.user.application.service;

import com.jongho.user.domain.model.redis.CachedUserProfile;
import com.jongho.user.application.repository.UserRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRedisServiceImpl implements UserRedisService {
    private final UserRedisRepository userRedisRepository;

    @Override
    public CachedUserProfile getUserProfileByUserId(Long userId){
        return userRedisRepository.selectUserProfileByUserId(userId);
    }

    @Override
    public void createUserProfileByUserId(Long userId, String nickname, String profileImage){
        userRedisRepository.createUserProfileByUserId(userId, nickname, profileImage);
    }
}
