package com.jongho.user.infra.repository;

import com.jongho.user.infra.mapper.UserNotificationSettingMapper;
import com.jongho.user.domain.model.UserNotificationSetting;
import com.jongho.user.application.repository.IUserNotificationSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserNotificationSettingRepository implements
    IUserNotificationSettingRepository {
    private final UserNotificationSettingMapper userNotificationSettingMapper;

    public int createUserNotificationSetting(UserNotificationSetting userNotificationSetting) {
        return userNotificationSettingMapper.createUserNotificationSetting(userNotificationSetting);
    }
}
