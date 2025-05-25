package com.jongho.user.application.service;

import com.jongho.user.application.repository.UserNotificationSettingRepository;
import com.jongho.user.domain.model.UserNotificationSetting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationSettingServiceImpl {
    private final UserNotificationSettingRepository userNotificationSettingRepository;

    public void createUserNotificationSetting(Long userId) {
        UserNotificationSetting userNotificationSetting = new UserNotificationSetting(userId);
        userNotificationSettingRepository.createUserNotificationSetting(userNotificationSetting);
    }
}
