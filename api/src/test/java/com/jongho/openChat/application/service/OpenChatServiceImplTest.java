package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.application.repository.OpenChatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("OpenChatRedisServiceImpl 클래스")
public class OpenChatServiceImplTest {
    @Mock
    private OpenChatRepository openChatRepository;
    @InjectMocks
    private OpenChatServiceImpl openChatService;
    @Nested
    @DisplayName("getLastOpenChatByOpenChatRoomId 메서드는")
    class Describe_getLastOpenChatByOpenChatRoomId{
        @Test
        @DisplayName("openChatRepository.selectLastOpenChatByChatRoomId 메서드를 호출하고 반환받은 값을 반환한다.")
        void openChatRepository_selectLastOpenChatByChatRoomId_메서드를_호출하고_반환받은_값을_반환한다(){
            // given
            Long chatRoomId = 1L;
            Optional<OpenChat> openChat = Optional.of(new OpenChat(
                    1L,
                    1L,
                    1L,
                    "test",
                    1,
                    0,
                    null,
                    "2024-01-29 00:00:00"
            ));
            when(openChatRepository.selectLastOpenChatByChatRoomId(chatRoomId)).thenReturn(openChat);

            // when
            Optional<OpenChat> result = openChatService.getLastOpenChatByOpenChatRoomId(chatRoomId);

            // then
            verify(openChatRepository).selectLastOpenChatByChatRoomId(chatRoomId);
            assertEquals(openChat, result);
        }
    }
    @Nested
    @DisplayName("getUnReadOpenChatCountByOpenChatRoomIdAndLastExitTime 메서드는")
    class Describe_getUnReadOpenChatCountByOpenChatRoomIdAndLastExitTime{
        @Test
        @DisplayName("openChatRepository.selectUnReadOpenChatCountByChatRoomIdAndLastExitTime 메서드를 호출하고 반환받은 값을 반환한다.")
        void openChatRepository_selectUnReadOpenChatCountByChatRoomIdAndLastExitTime_메서드를_호출하고_반환받은_값을_반환한다(){
            // given
            Long chatRoomId = 1L;
            String lastExitTime = "2024-01-29 00:00:00";
            int unReadOpenChatCount = 1;
            int limit = 150;
            when(openChatRepository.selectUnReadOpenChatCountByChatRoomIdAndLastExitTime(chatRoomId, lastExitTime, limit)).thenReturn(unReadOpenChatCount);

            // when
            int result = openChatService.getUnReadOpenChatCountByOpenChatRoomIdAndLastExitTime(chatRoomId, lastExitTime, limit);

            // then
            verify(openChatRepository).selectUnReadOpenChatCountByChatRoomIdAndLastExitTime(chatRoomId, lastExitTime, limit);
            assertEquals(unReadOpenChatCount, result);
        }
    }
}
