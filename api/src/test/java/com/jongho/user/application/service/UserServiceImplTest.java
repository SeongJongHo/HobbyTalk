package com.jongho.user.application.service;

import com.jongho.common.exception.UserDuplicatedException;
import com.jongho.common.exception.UserNotFoundException;
import com.jongho.user.application.dto.request.UserSignUpDto;
import com.jongho.user.domain.model.User;
import com.jongho.user.application.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImpl 클래스")
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private UserSignUpDto user;
    @Nested
    @DisplayName("signUp 메소드는")
    class Describe_signUp {

        @BeforeEach
        void setUp() {
            user = new UserSignUpDto("jonghao", "a123b123", "whdgh9595", "01012341234", null);
        }
        @Test()
        @DisplayName("UserRepository.findByUsername()을 호출하고 결과가 있으면 UserDuplicatedException을 발생시킨다.")
        void 유저_아이디가_중복된_경우_UserDuplicatedException을_발생시킨다() {
            // given
            when(userRepository.findOneByUsername(user.username())).thenReturn(Optional.of(user.toUser()));

            // when // then
            UserDuplicatedException e = assertThrows(UserDuplicatedException.class, () -> userService.signUp(user));
            assertEquals("이미 존재하는 아이디입니다.", e.getMessage());
        }

        @Test()
        @DisplayName("UserRepository.findByPhoneNumber()을 호출하고 결과가 있으면 UserDuplicatedException을 발생시킨다.")
        void 유저_핸드폰번호가_중복된_경우_UserDuplicatedException을_발생시킨다() {
            // given
            when(userRepository.findOneByPhoneNumber(user.phoneNumber())).thenReturn(Optional.of(user.toUser()));

            // when // then
            UserDuplicatedException e = assertThrows(UserDuplicatedException.class, () -> userService.signUp(user));
            assertEquals("이미 가입된 전화번호입니다.", e.getMessage());
        }

        @Test()
        @DisplayName("핸드폰번호와 아이디가 중복되지 않으면 유저를 생성하고 회원가입이 완료된다.")
        void 핸드폰번호와_아이디가_중복되지_않으면_유저를_생성하고_회원가입이_완료된다() {
            // given
            when(userRepository.findOneByUsername(user.username())).thenReturn(Optional.empty());
            when(userRepository.findOneByPhoneNumber(user.phoneNumber())).thenReturn(Optional.empty());

            // when
            userService.signUp(user);

            // then
            verify(userRepository, times(1)).createUser(any());
        }
    }

    @Nested
    @DisplayName("getUser 메소드는")
    class Describe_getUser {
        @Test
        @DisplayName("UserRepository.findByUsername()을 호출하고 결과가 없으면 UserNotFoundException을 발생시킨다.")
        void 유저_아이디로_유저를_가져와서_결과가_없으면_UserNotFoundException을_발생시킨다() {
            // given
            User user = new User(1L, "whdgh9595", "a123b123", "whdgh9595", "01012341234", null);
            when(userRepository.findOneByUsername(user.getUsername())).thenReturn(Optional.empty());

            // when then
            assertThrows(UserNotFoundException.class, () -> userService.getUser("whdgh9595"));
        }

        @Test
        @DisplayName("UserRepository.findByUsername()을 호출하고 결과가 있으면 유저를 반환한다.")
        void 유저_아이디로_유저를_가져와서_결과가_있으면_유저를_반환한다() {
            // given
            User user = new User(1L, "whdgh9595", "a123b123", "whdgh9595", "01012341234", null);
            when(userRepository.findOneByUsername(user.getUsername())).thenReturn(Optional.of(user));

            // when
            User result = userService.getUser("whdgh9595");

            // then
            assertEquals(user, result);
        }
    }

    @Nested
    @DisplayName("getUserById 메소드는")
    class Describe_getUserById {
        @Test
        @DisplayName("UserRepository.findById()을 호출하고 결과가 있으면 유저를 반환한다.")
        void 유저_아이디로_유저를_가져와서_결과가_있으면_유저를_반환한다() {
            // given
            User user = new User(1L, "whdgh9595", "a123b123", "whdgh9595", "01012341234", null);
            when(userRepository.findOneById(user.getId())).thenReturn(Optional.of(user));

            // when
            User result = userService.getUserById(1L).orElseThrow();

            // then
            assertEquals(user, result);
        }
    }
}
