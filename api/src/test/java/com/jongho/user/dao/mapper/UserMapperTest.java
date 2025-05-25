package com.jongho.user.dao.mapper;

import com.jongho.common.dao.BaseMapperTest;
import com.jongho.user.domain.model.User;
import com.jongho.user.infra.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("UserMapper 인터페이스")
public class UserMapperTest extends BaseMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {
        @AfterEach
        void setUp() {
            cleanUpUserTable();
        }
        @Test
        @DisplayName("인자로 받은 유저를 저장한다.")
        void 유저를_생성한다() {
            // given
            User user = new User("jonghao", "a123b123", "whdgh9595", "01012341234", null);

            // when
            userMapper.createUser(user);

            // then
            assertEquals(1, userMapper.findOneByUsername(user.getUsername()).getId());
        }

        @Test
        @DisplayName("인자로 받은 username으로 유저를 조회한다.")
        void 유저를_조회한다() {
            // given
            User user = new User("jonghao", "a123b123", "whdgh9595", "01012341234", null);

            // when
            userMapper.createUser(user);
            User selectUser = userMapper.findOneByUsername(user.getUsername());

            // then
            assertEquals(user.getUsername(), selectUser.getUsername());
        }
    }

    @Nested
    @DisplayName("findOneById 메소드는")
    class Describe_findOneById {
        @AfterEach
        void setUp() {
            cleanUpUserTable();
        }
        @Test
        @DisplayName("인자로 받은 id로 유저를 조회한다.")
        void 유저를_조회한다() {
            // given
            User user = new User("jonghao", "a123b123", "whdgh9595", "01012341234", null);

            // when
            userMapper.createUser(user);
            User selectUser = userMapper.findOneById(user.getId());

            // then
            assertEquals(user.getUsername(), selectUser.getUsername());
        }
    }
}