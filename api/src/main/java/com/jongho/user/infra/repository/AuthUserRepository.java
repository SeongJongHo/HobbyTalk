package com.jongho.user.infra.repository;

import com.jongho.user.domain.model.AuthUser;
import com.jongho.user.infra.mapper.AuthUserMapper;
import com.jongho.user.application.repository.IAuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthUserRepository implements IAuthUserRepository {
    private final AuthUserMapper authUserMapper;

    public void createAuthUser(AuthUser authUser) {
        authUserMapper.createAuthUser(authUser);
    }

    public void updateRefreshToken(AuthUser authUser) {

        System.out.println("authUserMapper.updateRefreshToken(authUser) : " + authUser.getRefreshToken());
        authUserMapper.updateRefreshToken(authUser);
    }

    public Optional<AuthUser> selectOneAuthUser(Long userId) {

        return Optional.ofNullable(authUserMapper.selectOneAuthUser(userId));
    }
}
