package com.jongho.user.application.facade;

import com.jongho.user.application.dto.request.UserSignUpDto;
import com.jongho.user.application.service.UserNotificationSettingService;
import com.jongho.user.application.service.UserService;
import com.jongho.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade{
    private final UserService userService;
    private final UserNotificationSettingService userNotificationSettingService;
    @Override
    @Transactional
    public void signUpUserAndCreateNotificationSetting(UserSignUpDto userSignUpDto) {
        userService.signUp(userSignUpDto);
        User user = userService.getUser(userSignUpDto.username());
        userNotificationSettingService.createUserNotificationSetting(user.getId());
    }
}
