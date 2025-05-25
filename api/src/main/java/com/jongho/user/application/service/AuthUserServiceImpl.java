package com.jongho.user.application.service;

import com.jongho.user.application.repository.AuthUserRepository;
import com.jongho.user.domain.model.AuthUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl {
    private final AuthUserRepository authUserRepository;
    public void createAuthUser(AuthUser authUser) {
        authUserRepository.createAuthUser(authUser);
    }

    public void updateRefreshToken(AuthUser authUser) {
        authUserRepository.updateRefreshToken(authUser);
    }

    public Optional<AuthUser> getAuthUser(Long userId) {
        return authUserRepository.selectOneAuthUser(userId);
    }
}
