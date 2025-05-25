package com.jongho.user.dao.mapper;

import com.jongho.common.dao.BaseMapperTest;
import com.jongho.user.domain.model.AuthUser;
import com.jongho.user.infra.mapper.AuthUserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("AuthUserMapper 인터페이스")
public class AuthUserMapperTest extends BaseMapperTest {
    @Autowired
    private AuthUserMapper authUserMapper;

    @BeforeEach
    void setUp() {
        cleanUpAuthUserTable();

    }
    @Nested
    @DisplayName("selectOneAuthUser 메소드는")
    class Describe_selectOneAuthUser {
        @Test
        @DisplayName("인자로 받은 유저의 인증정보를 조회한다.")
        void 인자로_받은_인증유저_조회한다() {
            // given
            AuthUser authUser = new AuthUser(1L, "refreshToken");
            authUserMapper.createAuthUser(authUser);

            // when
            AuthUser actualAuthUser = authUserMapper.selectOneAuthUser(authUser.getUserId());

            // then
            assertEquals(authUser.getUserId(), actualAuthUser.getUserId());
            assertEquals(authUser.getRefreshToken(), actualAuthUser.getRefreshToken());
        }
    }

    @Nested
    @DisplayName("createAuthUser 메소드는")
    class Describe_createAuthUser {
        @Test
        @DisplayName("인자로 받은 유저를 저장한다.")
        void 인자로_받은_인증유저_생성한다() {
            // given
            AuthUser authUser = new AuthUser(1L, "refreshToken");

            // when
            authUserMapper.createAuthUser(authUser);

            // then
            assertEquals(authUser.getUserId(), authUserMapper.selectOneAuthUser(authUser.getUserId()).getUserId());
        }
    }

    @Nested
    @DisplayName("updateRefreshToken 메소드는")
    class Describe_updateRefreshToken {
        @Test
        @DisplayName("인자로 받은 유저의 refreshToken을 업데이트한다,.")
        void 인자로_받은_인증유저의_refresh_token을_업데이트한다() {
            // given
            authUserMapper.createAuthUser(new AuthUser(1L, "token"));
            AuthUser authUser = new AuthUser(1L, "refreshToken");

            // when
            authUserMapper.updateRefreshToken(authUser);
            String actualRefreshToken = authUserMapper.selectOneAuthUser(authUser.getUserId()).getRefreshToken();

            // then
            assertEquals(authUser.getRefreshToken(), actualRefreshToken);
        }
    }

}
