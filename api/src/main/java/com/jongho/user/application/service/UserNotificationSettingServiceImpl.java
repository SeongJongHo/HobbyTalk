package com.jongho.user.application.service;

import com.jongho.user.domain.model.UserNotificationSetting;
import com.jongho.user.application.repository.UserNotificationSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationSettingServiceImpl implements UserNotificationSettingService{
    private final UserNotificationSettingRepository userNotificationSettingRepository;
    @Override
    public void createUserNotificationSetting(Long userId) {
        UserNotificationSetting userNotificationSetting = new UserNotificationSetting(userId);
        userNotificationSettingRepository.createUserNotificationSetting(userNotificationSetting);
    }
}
