package com.jongho.user.infra.mapper;

import com.jongho.user.domain.model.UserNotificationSetting;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserNotificationSettingMapper {
    public int createUserNotificationSetting(UserNotificationSetting userNotificationSetting);
}
