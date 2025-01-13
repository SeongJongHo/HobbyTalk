package com.jongho.user.dao.mapper;

import com.jongho.common.dao.BaseMapperTest;
import com.jongho.user.domain.model.UserNotificationSetting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("UserNotificationSettingMapper 인터페이스")
public class UserNotificationSettingMapperTest extends BaseMapperTest {
    @Autowired
    private UserNotificationSettingMapper userNotificationSettingMapper;

    @Nested
    @DisplayName("createUserNotificationSetting 메소드는")
    class Describe_createUserNotificationSetting {
        @AfterEach
        void setUp() {
            cleanUpUserNotificationSettingTable();
        }
        @Test
        @DisplayName("인자로 받은 유저 알림 설정을 저장한다.")
        void 유저_알림_설정을_생성한다() {
            // given
            UserNotificationSetting userNotificationSetting = new UserNotificationSetting(1L, true, true, true);

            // when then
            assertEquals(1, userNotificationSettingMapper.createUserNotificationSetting(userNotificationSetting));
        }
    }
}
