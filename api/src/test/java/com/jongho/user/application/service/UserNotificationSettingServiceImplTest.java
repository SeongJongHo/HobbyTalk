package com.jongho.user.application.service;

import com.jongho.user.domain.model.UserNotificationSetting;
import com.jongho.user.application.repository.UserNotificationSettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserNotificationSettingServiceImpl 클래스")
public class UserNotificationSettingServiceImplTest {
    @Mock
    private UserNotificationSettingRepository userNotificationSettingRepository;
    @InjectMocks
    private UserNotificationSettingServiceImpl userNotificationSettingService;
    private UserNotificationSetting userNotificationSetting;
    @Nested
    @DisplayName("createUserNotificationSetting 메소드는")
    class Describe_createUserNotificationSetting {
        @BeforeEach
        void setUp() {
            userNotificationSetting = new UserNotificationSetting(1L, true, true, true);
        }
        @Test
        @DisplayName("UserNotificationSettingRepository.createUserNotificationSetting()을 호출한다.")
        void 유저_알림_설정을_생성한다() {
            // given
            Long userId = 1L;
            when(userNotificationSettingRepository.createUserNotificationSetting(userNotificationSetting)).thenReturn(1);

            // when
            userNotificationSettingService.createUserNotificationSetting(userId);

            // then
            verify(userNotificationSettingRepository, times(1)).createUserNotificationSetting(userNotificationSetting);
        }
    }
}
