package com.jongho.user.application.service;

import com.jongho.user.domain.model.AuthUser;
import com.jongho.user.application.repository.IAuthUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthUserServiceImpl 클래스")
public class AuthUserServiceTest {
    @Mock
    private IAuthUserRepository IAuthUserRepository;
    @InjectMocks
    private AuthUserService authUserService;

    @Nested
    @DisplayName("createAuthUser 메소드는")
    class Describe_createAuthUser {
            @Test
            @DisplayName("AuthUserRepository의 createAuthUser 메소드를 호출한다")
            void AuthUserRepository의_createAuthUser_메소드를_한번_호출한다() {
                // given
                AuthUser authUser = new AuthUser(1L, "refreshToken");
                doNothing().when(IAuthUserRepository).createAuthUser(authUser);

                // when
                authUserService.createAuthUser(authUser);

                // then
                verify(IAuthUserRepository, times(1)).createAuthUser(authUser);
            }
    }

    @Nested
    @DisplayName("updateRefreshToken 메소드는")
    class Describe_updateRefreshToken {
            @Test
            @DisplayName("AuthUserRepository의 updateRefreshToken 메소드를 호출한다")
            void AuthUserRepository의_updateRefreshToken_메소드를_한번_호출한다() {
                // given
                AuthUser authUser = new AuthUser(1L, "refreshToken");
                doNothing().when(IAuthUserRepository).updateRefreshToken(authUser);

                // when
                authUserService.updateRefreshToken(authUser);

                // then
                verify(IAuthUserRepository, times(1)).updateRefreshToken(authUser);
            }
    }

    @Nested
    @DisplayName("getAuthUser 메소드는")
    class Describe_getAuthUser {
            @Test
            @DisplayName("AuthUserRepository의 selectOneAuthUser 메소드를 호출한다")
            void AuthUserRepository의_selectOneAuthUser_메소드를_한번_호출한다() {
                // given
                Long userId = 1L;
                AuthUser authUser = new AuthUser(1L, "refreshToken");
                when(IAuthUserRepository.selectOneAuthUser(userId)).thenReturn(Optional.of(authUser));

                // when
                Optional<AuthUser> result = authUserService.getAuthUser(userId);

                // then
                verify(IAuthUserRepository, times(1)).selectOneAuthUser(userId);
                assertEquals(Optional.of(authUser), result);
            }
    }
}
