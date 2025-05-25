package com.jongho.user.application.repository;

import com.jongho.user.domain.model.AuthUser;

import java.util.Optional;

public interface IAuthUserRepository {
    public void createAuthUser(AuthUser authUser);
    public void updateRefreshToken(AuthUser authUser);
    public Optional<AuthUser> selectOneAuthUser(Long userId);
}
