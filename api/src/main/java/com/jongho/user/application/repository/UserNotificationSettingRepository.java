package com.jongho.user.application.repository;

import com.jongho.user.domain.model.UserNotificationSetting;

public interface UserNotificationSettingRepository {
    public int createUserNotificationSetting(UserNotificationSetting userNotificationSetting);
}
