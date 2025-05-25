package com.jongho.user.infra.mapper;

import com.jongho.user.domain.model.AuthUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthUserMapper {
    public void createAuthUser(AuthUser authUser);
    public void updateRefreshToken(AuthUser authUser);
    public AuthUser selectOneAuthUser(@Param("userId") Long userId);
}
