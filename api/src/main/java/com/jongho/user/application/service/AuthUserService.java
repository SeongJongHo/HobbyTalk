package com.jongho.user.application.service;

import com.jongho.user.application.repository.IAuthUserRepository;
import com.jongho.user.domain.model.AuthUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final IAuthUserRepository IAuthUserRepository;
    public void createAuthUser(AuthUser authUser) {
        IAuthUserRepository.createAuthUser(authUser);
    }

    public void updateRefreshToken(AuthUser authUser) {
        IAuthUserRepository.updateRefreshToken(authUser);
    }

    public Optional<AuthUser> getAuthUser(Long userId) {
        return IAuthUserRepository.selectOneAuthUser(userId);
    }
}
