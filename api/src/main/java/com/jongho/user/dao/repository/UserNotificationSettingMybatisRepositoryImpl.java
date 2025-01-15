package com.jongho.user.dao.repository;

import com.jongho.user.dao.mapper.UserNotificationSettingMapper;
import com.jongho.user.domain.model.UserNotificationSetting;
import com.jongho.user.application.repository.UserNotificationSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserNotificationSettingMybatisRepositoryImpl implements UserNotificationSettingRepository {
    private final UserNotificationSettingMapper userNotificationSettingMapper;

    public int createUserNotificationSetting(UserNotificationSetting userNotificationSetting) {
        return userNotificationSettingMapper.createUserNotificationSetting(userNotificationSetting);
    }
}
