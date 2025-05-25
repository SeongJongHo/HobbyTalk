package com.jongho.user.dao.repository;

import com.jongho.user.dao.mapper.UserNotificationSettingMapper;
import com.jongho.user.domain.model.UserNotificationSetting;
import com.jongho.user.application.repository.IUserNotificationSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserNotificationSettingMybatisRepository implements
    IUserNotificationSettingRepository {
    private final UserNotificationSettingMapper userNotificationSettingMapper;

    public int createUserNotificationSetting(UserNotificationSetting userNotificationSetting) {
        return userNotificationSettingMapper.createUserNotificationSetting(userNotificationSetting);
    }
}
