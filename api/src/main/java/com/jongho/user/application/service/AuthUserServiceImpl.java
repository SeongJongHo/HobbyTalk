package com.jongho.user.application.service;

import com.jongho.user.domain.model.AuthUser;
import com.jongho.user.application.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService{
    private final AuthUserRepository authUserRepository;
    @Override
    public void createAuthUser(AuthUser authUser) {
        authUserRepository.createAuthUser(authUser);
    }

    @Override
    public void updateRefreshToken(AuthUser authUser) {
        authUserRepository.updateRefreshToken(authUser);
    }

    @Override
    public Optional<AuthUser> getAuthUser(Long userId) {
        return authUserRepository.selectOneAuthUser(userId);
    }
}
