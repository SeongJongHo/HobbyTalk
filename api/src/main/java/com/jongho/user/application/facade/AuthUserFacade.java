package com.jongho.user.application.facade;

import com.jongho.common.exception.UnAuthorizedException;
import com.jongho.common.util.bcrypt.BcryptUtil;
import com.jongho.common.util.jwt.AccessPayload;
import com.jongho.common.util.jwt.JwtUtil;
import com.jongho.common.util.jwt.RefreshPayload;
import com.jongho.user.application.dto.response.TokenResponseDto;
import com.jongho.user.application.service.AuthUserService;
import com.jongho.user.application.service.UserService;
import com.jongho.user.domain.model.AuthUser;
import com.jongho.user.domain.model.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthUserFacade {

    private final UserService userService;
    private final AuthUserService authUserService;
    private final JwtUtil jwtUtil;
    @Transactional
    public TokenResponseDto signIn(String username, String password) {
        User user = userService.getUser(username);
        if(!BcryptUtil.checkPassword(password, user.getPassword())) {
            throw new UnAuthorizedException("비밀번호가 일치하지 않습니다.");
        }
        AccessPayload accessPayload = new AccessPayload(user.getId());
        RefreshPayload refreshPayload = new RefreshPayload(user.getId());

        String refreshToken = jwtUtil.createRefreshToken(refreshPayload);
        Optional<AuthUser> authUser = authUserService.getAuthUser(user.getId());
        if(authUser.isPresent()) {

            authUserService.updateRefreshToken(new AuthUser(user.getId(), refreshToken));
        } else {

            authUserService.createAuthUser(new AuthUser(user.getId(), refreshToken));
        }

        return new TokenResponseDto(jwtUtil.createAccessToken(accessPayload), refreshToken);
    }

    @Transactional
    public TokenResponseDto tokenRefresh(String refreshToken) {
        RefreshPayload refreshPayload = jwtUtil.validateRefreshToken(refreshToken);
        Optional<AuthUser> authUser = authUserService.getAuthUser(refreshPayload.getUserId());
        if(authUser.isPresent()) {
            if(refreshToken.equals(authUser.get().getRefreshToken())) {
                User user = userService.getUserById(refreshPayload.getUserId())
                    .orElseThrow(() -> new UnAuthorizedException(
                        "존재하지 않는 유저입니다."));

                String newRefreshToken = jwtUtil.createRefreshToken(refreshPayload);
                authUserService.updateRefreshToken(new AuthUser(user.getId(), newRefreshToken));

                return new TokenResponseDto(jwtUtil.createAccessToken(new AccessPayload(user.getId())), newRefreshToken);
            }
        }
        throw new UnAuthorizedException("리프레시 토큰이 유효하지 않습니다.");
    }
}
