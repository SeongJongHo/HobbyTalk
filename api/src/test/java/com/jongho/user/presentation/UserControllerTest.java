package com.jongho.user.presentation;

import com.google.gson.Gson;
import com.jongho.common.config.WebMvcConfig;
import com.jongho.common.interceptor.AuthInterceptor;
import com.jongho.user.application.dto.request.UserSignInDto;
import com.jongho.user.application.dto.request.UserSignUpDto;
import com.jongho.user.application.dto.response.TokenResponseDto;
import com.jongho.user.application.facade.AuthUserFacade;
import com.jongho.user.application.facade.UserFacade;
import com.jongho.user.application.service.UserService;
import com.jongho.user.presentation.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = UserController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AuthInterceptor.class, WebMvcConfig.class})
)
@DisplayName("UserController 클래스")
public class UserControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private UserFacade userFacade;
    @MockBean
    private AuthUserFacade authUserFacade;
    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("signUp 메소드는")
    class Describe_signUp {
        private UserSignUpDto userSignUpDto;

        @BeforeEach
        public void setUp() {
            userSignUpDto = new UserSignUpDto("jonghao", "a123b123!", "whdgh9595", "01012341234", "abc.jpg");
        }

        @Test
        @DisplayName("올바른 데이터를 받으면 UserFacade.userSignUpAndNotificationSettingCreate 을 호출하고 status 201과 user create라는 메세지를 반환한다.")
        void 올바른_데이터를_받으면_userFacade_userSignUpAndNotificationSettingCreate를_호출하고_status_201_및_user_create_메세지를_반환한다() throws Exception {
            // given
            doNothing().when(userFacade).signUpUserAndCreateNotificationSetting(userSignUpDto);
            Gson gson = new Gson();
            String userSignUpDtoJson = gson.toJson(Map.of(
                    "nickname", userSignUpDto.nickname(),
                    "password", userSignUpDto.password(),
                    "username", userSignUpDto.username(),
                    "phone_number", userSignUpDto.phoneNumber(),
                    "profile_image", userSignUpDto.profileImage()
            ));

            // when
            mockMvc.perform(post("/api/v1/users/sign-up")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userSignUpDtoJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.message").value("user create"))
                    .andDo(print());

            // then
            verify(userFacade, times(1)).signUpUserAndCreateNotificationSetting(userSignUpDto);
        }
    }

    @Nested
    @DisplayName("signIn 메소드는")
    class Describe_signIn {
        private UserSignInDto userSignInDto;

        @BeforeEach
        public void setUp() {
            userSignInDto = new UserSignInDto("a123b123!@", "jonghao");
        }

        @Test
        @DisplayName("올바른 데이터를 받으면 AuthUserFacade.signIn 을 호출하고 status 200과 token을 반환한다.")
        void 올바른_데이터를_받으면_AuthUserFacade_signIn을_호출하고_status200과_token을_반환한다() throws Exception {
            // given
            TokenResponseDto result = new TokenResponseDto("token", "refreshToken");

            when(authUserFacade.signIn(userSignInDto.getUsername(), userSignInDto.getPassword())).thenReturn(result);
            Gson gson = new Gson();
            String userSignUpDtoJson = gson.toJson(userSignInDto);

            // when
            mockMvc.perform(post("/api/v1/users/sign-in")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userSignUpDtoJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("success"))
                    .andExpect(jsonPath("$.data.accessToken").exists())
                    .andExpect(jsonPath("$.data.refreshToken").exists())
                    .andDo(print());

            // then
            verify(authUserFacade, times(1)).signIn(userSignInDto.getUsername(), userSignInDto.getPassword());
        }
    }

    @Nested
    @DisplayName("tokenRefresh 메소드는")
    class Describe_tokenRefresh {
        @Test
        @DisplayName("올바른 데이터를 받으면 AuthUserFacade.tokenRefresh 을 호출하고 status 200과 token을 반환한다.")
        void 올바른_데이터를_받으면_AuthUserFacade_tokenRefresh을_호출하고_status200과_token을_반환한다() throws Exception {
            // given
            TokenResponseDto result = new TokenResponseDto("token", "refreshToken");

            when(authUserFacade.tokenRefresh("refreshToken")).thenReturn(result);
            Gson gson = new Gson();
            String userSignUpDtoJson = gson.toJson(result);

            // when
            mockMvc.perform(post("/api/v1/users/token-refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userSignUpDtoJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("success"))
                    .andExpect(jsonPath("$.data.accessToken").exists())
                    .andExpect(jsonPath("$.data.refreshToken").exists())
                    .andDo(print());

            // then
            verify(authUserFacade, times(1)).tokenRefresh("refreshToken");
        }
    }
}
