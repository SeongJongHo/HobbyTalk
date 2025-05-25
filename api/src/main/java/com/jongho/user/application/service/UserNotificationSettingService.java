package com.jongho.user.application.service;

import com.jongho.user.application.repository.IUserNotificationSettingRepository;
import com.jongho.user.domain.model.UserNotificationSetting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationSettingService {
    private final IUserNotificationSettingRepository IUserNotificationSettingRepository;

    public void createUserNotificationSetting(Long userId) {
        UserNotificationSetting userNotificationSetting = new UserNotificationSetting(userId);
        IUserNotificationSettingRepository.createUserNotificationSetting(userNotificationSetting);
    }
}
