package com.jongho.user.presentation.controller;

import com.jongho.common.annotaition.HttpRequestLogging;
import com.jongho.common.response.BaseResponseEntity;
import com.jongho.user.application.dto.request.TokenRefreshDto;
import com.jongho.user.application.dto.request.UserSignInDto;
import com.jongho.user.application.dto.request.UserSignUpDto;
import com.jongho.user.application.dto.response.TokenResponseDto;
import com.jongho.user.application.facade.AuthUserFacadeImpl;
import com.jongho.user.application.facade.UserFacadeImpl;
import com.jongho.user.application.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 API")
@HttpRequestLogging
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final UserFacadeImpl userFacade;
    private final AuthUserFacadeImpl authUserFacade;

    @Operation(summary = "회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponseEntity<?>> signUp(@Parameter @Validated @RequestBody UserSignUpDto userSignUpDto) {
        userFacade.signUpUserAndCreateNotificationSetting(userSignUpDto);

        return BaseResponseEntity.create("user create");
    }

    @Operation(summary = "로그인")
    @PostMapping("/sign-in")
    public ResponseEntity<BaseResponseEntity<TokenResponseDto>> signIn(@Validated @RequestBody UserSignInDto userSignUpDto) {
        TokenResponseDto result = authUserFacade.signIn(userSignUpDto.getUsername(), userSignUpDto.getPassword());

        return BaseResponseEntity.ok(result, "success");
    }

    @Operation(summary = "토큰 리프레시")
    @PostMapping("/token-refresh")
    public ResponseEntity<BaseResponseEntity<TokenResponseDto>> tokenRefresh(@RequestBody TokenRefreshDto tokenRefreshDto) {
        TokenResponseDto result = authUserFacade.tokenRefresh(tokenRefreshDto.getRefreshToken());

        return BaseResponseEntity.ok(result, "success");
    }
}