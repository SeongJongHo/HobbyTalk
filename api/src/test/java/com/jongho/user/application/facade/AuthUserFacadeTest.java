package com.jongho.user.application.facade;

import com.jongho.common.exception.UnAuthorizedException;
import com.jongho.common.util.bcrypt.BcryptUtil;
import com.jongho.user.domain.model.AuthUser;
import com.jongho.user.application.dto.request.UserSignInDto;
import com.jongho.user.application.dto.response.TokenResponseDto;
import com.jongho.user.application.service.AuthUserService;
import com.jongho.user.application.service.UserService;
import com.jongho.user.domain.model.User;
import com.jongho.common.util.jwt.AccessPayload;
import com.jongho.common.util.jwt.JwtUtil;
import com.jongho.common.util.jwt.RefreshPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthUserFacadeImpl 클래스")
public class AuthUserFacadeTest {
    @Mock
    private UserService userService;
    @Mock
    private AuthUserService authUserService;
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthUserFacade authUserFacade;


    @Nested
    @MockitoSettings(strictness = Strictness.LENIENT)
    @DisplayName("signIn 메소드는")
    class Describe_signIn {
        private UserSignInDto userSignInDto;
        private User user;
        private AuthUser authUser;
        private AccessPayload accessPayload;
        private RefreshPayload refreshPayload;
        private OngoingStubbing<String> mockJwtUtilCreateAccessToken;
        private OngoingStubbing<String> mockJwtUtilCreateRefreshToken;
        private OngoingStubbing<Optional<AuthUser>> mockAuthUserServiceGetAuthUser;
        private OngoingStubbing<User> mockUserServiceGetUser;
        private final String accessToekn = "token";
        private final String refreshToken = "refreshToken";


        @BeforeEach
        void setUp() {
            userSignInDto = new UserSignInDto("jonghao1@", "whdgh9595");
            user = new User(1L, "whdgh9595", BcryptUtil.hashPassword("jonghao1@"), "whdgh9595", "01012341234", null);
            authUser = new AuthUser(user.getId(), refreshToken);
            accessPayload = new AccessPayload(user.getId());
            refreshPayload = new RefreshPayload(user.getId());
            mockJwtUtilCreateAccessToken = when(jwtUtil.createAccessToken(accessPayload)).thenReturn(accessToekn);
            mockJwtUtilCreateRefreshToken = when(jwtUtil.createRefreshToken(refreshPayload)).thenReturn(refreshToken);
            mockUserServiceGetUser = when(userService.getUser(userSignInDto.getUsername())).thenReturn(user);
            mockAuthUserServiceGetAuthUser = when(authUserService.getAuthUser(user.getId())).thenReturn(Optional.empty());
            doNothing().when(authUserService).createAuthUser(authUser);
        }
        @Test
        @DisplayName("정상적인 데이터를 받으면 UserService.getUser를 호출하고 가져온 데이터의 패스워드와 요청받은 패스워드가 같으면 JwtUtil.createToken을 호출하고 토큰을 반환한다.")
        void 성공적인_요청의경우_아이디와_일치하는_데이터를_가져와_비밀번호를_비교하고_맞으면_토큰을_발급하고_반환한다(){
            // when
            TokenResponseDto result = authUserFacade.signIn(userSignInDto.getUsername(), userSignInDto.getPassword());

            // then
             verify(userService, times(1)).getUser(userSignInDto.getUsername());
             verify(authUserService, times(1)).getAuthUser(user.getId());
             verify(authUserService, times(1)).createAuthUser(authUser);
             verify(jwtUtil, times(1)).createAccessToken(accessPayload);
             verify(jwtUtil, times(1)).createRefreshToken(refreshPayload);
             assertEquals("token", result.getAccessToken());
             assertEquals("refreshToken", result.getRefreshToken());

        }

        @Test
        @DisplayName("UserService.getUser를 호출하고 가져온 데이터의 패스워드와 요청받은 패스워드가 다르면 UnAuthorizedException을 발생시킨다.")
        void 전달받은_패스워드와_데이터이_패스워드가_다르면_UnAuthorizedException을_발생시킨다(){
            // given
            userSignInDto = new UserSignInDto("jonghao1!", "whdgh9595");

            // when then
            Exception e = assertThrows(UnAuthorizedException.class,() -> authUserFacade.signIn(userSignInDto.getUsername(), userSignInDto.getPassword()));
            assertEquals("비밀번호가 일치하지 않습니다.", e.getMessage());
        }

        @Test
        @DisplayName("한번 이상 로그인을 해서 AuthUser가 존재하면 updateRefreshToken을 호출한다.")
        void 한번_이상_로그인을_해서_AuthUser가_존재하면_AuthUserService_updateRefreshToken을_호출한다(){
            // given
            when(authUserService.getAuthUser(user.getId())).thenReturn(Optional.of(authUser));

            // when
            authUserFacade.signIn(userSignInDto.getUsername(), userSignInDto.getPassword());

            // then
            verify(authUserService, times(1)).updateRefreshToken(authUser);
        }

        @Test
        @DisplayName("한번도 로그인을 안해서 AuthUser가 존재하지 않으면 createAuthUser를 호출한다.")
        void 한번도_로그인을_안해서_AuthUser가_존재하지_않으면_AuthUserService_create을_호출한다(){
            // when
            authUserFacade.signIn(userSignInDto.getUsername(), userSignInDto.getPassword());

            // then
            verify(authUserService, times(1)).createAuthUser(authUser);
        }

    }

    @Nested
    @MockitoSettings(strictness = Strictness.LENIENT)
    @DisplayName("tokenRefresh 메소드는")
    class Describe_tokenRefresh {
        private RefreshPayload refreshPayload;
        private AuthUser authUser;
        private User user;
        private OngoingStubbing<RefreshPayload> mockJwtUtilValidateRefreshToken;
        private OngoingStubbing<Optional<AuthUser>> mockAuthUserServiceGetAuthUser;
        private OngoingStubbing<Optional<User>> mockUserServiceGetUser;
        private OngoingStubbing<String> mockJwtUtilCreateAccessToken;
        private OngoingStubbing<String> mockJwtUtilCreateRefreshToken;
        private final String accessToekn = "token";
        private final String refreshToken = "refreshToken";

        @BeforeEach
        void setUp() {
            refreshPayload = new RefreshPayload(1L);
            authUser = new AuthUser(1L, refreshToken);
            user = new User(1L, "whdgh9595", BcryptUtil.hashPassword("jonghao1@"), "whdgh9595", "01012341234", null);
            mockJwtUtilValidateRefreshToken = when(jwtUtil.validateRefreshToken(refreshToken)).thenReturn(refreshPayload);
            mockAuthUserServiceGetAuthUser = when(authUserService.getAuthUser(refreshPayload.getUserId())).thenReturn(Optional.of(authUser));
            mockUserServiceGetUser = when(userService.getUserById(refreshPayload.getUserId())).thenReturn(Optional.of(user));
            mockJwtUtilCreateAccessToken = when(jwtUtil.createAccessToken(new AccessPayload(user.getId()))).thenReturn(accessToekn);
            mockJwtUtilCreateRefreshToken = when(jwtUtil.createRefreshToken(refreshPayload)).thenReturn(refreshToken);
            doNothing().when(authUserService).updateRefreshToken(new AuthUser(user.getId(), refreshToken));
        }

        @Test
        @DisplayName("정상적인 데이터를 받으면 토큰을 검증하고 새로운 RefreshToken과 AccessToken을 발급하고 토큰을 반환한다.")
        void 성공적인_요청의경우_토큰을_검증하고_새로운_RefreshToken과_AccessToken을_발급하고_토큰을_반환한다() {
            // when
            TokenResponseDto result = authUserFacade.tokenRefresh(refreshToken);

            // then
            verify(jwtUtil, times(1)).validateRefreshToken(refreshToken);
            verify(authUserService, times(1)).getAuthUser(refreshPayload.getUserId());
            verify(userService, times(1)).getUserById(refreshPayload.getUserId());
            verify(authUserService, times(1)).updateRefreshToken(new AuthUser(user.getId(), refreshToken));
            verify(jwtUtil, times(1)).createAccessToken(new AccessPayload(user.getId()));
            verify(jwtUtil, times(1)).createRefreshToken(refreshPayload);
            assertEquals("token", result.getAccessToken());
            assertEquals("refreshToken", result.getRefreshToken());
        }

        @Test
        @DisplayName("토큰의 유저 아이디로 AuthUser가 존재하지 않으면 UnAuthorizedException을 발생시킨다.")
        void 토큰의_유저아이디로_AuthUser가_존재하지_않으면_UnAuthorizedException을_반환한다() {
            // when
            mockAuthUserServiceGetAuthUser = when(authUserService.getAuthUser(refreshPayload.getUserId())).thenReturn(Optional.empty());

            // when then
            Exception e = assertThrows(UnAuthorizedException.class, () -> authUserFacade.tokenRefresh(refreshToken));
            assertEquals("리프레시 토큰이 유효하지 않습니다.", e.getMessage());
        }

        @Test
        @DisplayName("토큰의 유저 아이디로 AuthUser가 존재하지만 토큰이 일치하지 않으면 UnAuthorizedException을 발생시킨다.")
        void 토큰의_유저아이디로_AuthUser가_존재하지만_토큰이_일치하지_않으면_UnAuthorizedException을_반환한다() {
            // when
            mockAuthUserServiceGetAuthUser = when(authUserService.getAuthUser(refreshPayload.getUserId())).thenReturn(Optional.of(new AuthUser(1L, "token")));

            // when then
            Exception e = assertThrows(UnAuthorizedException.class, () -> authUserFacade.tokenRefresh(refreshToken));
            assertEquals("리프레시 토큰이 유효하지 않습니다.", e.getMessage());
        }
    }
}
