package com.jongho.openChatRoomUser.application.service;

import com.jongho.openChatRoomUser.domain.model.OpenChatRoomUser;
import com.jongho.openChatRoomUser.application.repository.OpenChatRoomUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OpenChatRoomUserServiceImpl 클래스")
public class OpenChatRoomUserServiceImplTest {
    @Mock
    private OpenChatRoomUserRepository openChatRoomUserRepository;
    @InjectMocks
    private OpenChatRoomUserServiceImpl openChatRoomUserServiceImpl;

    @Nested
    @DisplayName("createOpenChatRoomUser 메소드는")
    class Describe_createOpenChatRoomUser {
        @Test
        @DisplayName("OpenChatRoomUserRepository의 createOpenChatRoomUser 메소드를 호출한다")
        void OpenChatRoomUserRepository의의_createOpenChatRoomUser메소드를_한번_호출한다() {
            // given
            OpenChatRoomUser openChatRoomUser = new OpenChatRoomUser(
                    1L,
                    2L
            );
            doNothing().when(openChatRoomUserRepository).createOpenChatRoomUser(openChatRoomUser);

            // when
            openChatRoomUserServiceImpl.createOpenChatRoomUser(openChatRoomUser);

            // then
            verify(openChatRoomUserRepository, times(1)).createOpenChatRoomUser(openChatRoomUser);
        }
    }

    @Nested
    @DisplayName("getOpenChatRoomUserByOpenChatRoomIdAndUserId 메소드는")
    class Describe_countByUserId {
        @Test
        @DisplayName("OpenChatRoomUserRepository의 selectOpenChatRoomUserByOpenChatRoomIdAndUserId 메소드를 한번 호출하고 받은 값을 반환한다.")
        void OpenChatRoomUserRepository의_selectOpenChatRoomUserByOpenChatRoomIdAndUserId메소드를_한번_호출하고_받은_값을_반환한다() {
            // given
            Long managerId = 1L;
            Long openChatRoomId = 2L;
            when(openChatRoomUserRepository.selectOneOpenChatRoomUserByOpenChatRoomIdAndUserId(openChatRoomId, managerId)).thenReturn(Optional.of(new OpenChatRoomUser(
                    openChatRoomId,
                    managerId
            )));

            // when
            Optional<OpenChatRoomUser> openChatRoomUser = openChatRoomUserServiceImpl.getOpenChatRoomUserByOpenChatRoomIdAndUserId(openChatRoomId, managerId);

            // then
            assertEquals(managerId, openChatRoomUser.get().getUserId());
            assertEquals(openChatRoomId, openChatRoomUser.get().getOpenChatRoomId());
        }
    }
}
