package com.jongho.user.application.facade;

import com.jongho.user.application.dto.request.UserSignUpDto;
import com.jongho.user.application.service.UserNotificationSettingServiceImpl;
import com.jongho.user.application.service.UserServiceImpl;
import com.jongho.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl {

    private final UserServiceImpl userService;
    private final UserNotificationSettingServiceImpl userNotificationSettingService;

    @Transactional
    public void signUpUserAndCreateNotificationSetting(UserSignUpDto userSignUpDto) {
        userService.signUp(userSignUpDto);
        User user = userService.getUser(userSignUpDto.username());
        userNotificationSettingService.createUserNotificationSetting(user.getId());
    }
}
