package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.application.repository.OpenChatRedisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("OpenChatRedisServiceImpl 클래스")
public class OpenChatRedisServiceImplTest {
    @Mock
    private OpenChatRedisRepository openChatRedisRepository;
    @InjectMocks
    private OpenChatRedisServiceImpl openChatRedisService;
    @Nested
    @DisplayName("getLastOpenChatByOpenChatRoomId 메소드는")
    class Describe_getLastOpenChatByOpenChatRoomId {
        @Test
        @DisplayName("openChatRedisRepository.selectLastOpenChatByChatRoomId 메소드를 호출하고 받은 반환값을 반환한다.")
        void openChatRedisRepository_selectLastOpenChatByChatRoomId_메소드를_호출하고_받은_반환값을_반환한다() {
            // given
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
            when(openChatRedisRepository.selectLastOpenChatByChatRoomId(1L)).thenReturn(openChat);

            // when
            Optional<OpenChat> result = openChatRedisService.getLastOpenChatByOpenChatRoomId(1L);

            // then
            verify(openChatRedisRepository).selectLastOpenChatByChatRoomId(1L);
            assertEquals(openChat, result);
        }
        @Nested
        @DisplayName("getOpenChatListByOpenChatRoomId 메소드는")
        class Describe_getOpenChatListByOpenChatRoomId {
            @Test
            @DisplayName("openChatRedisRepository.selectOpenChatListByChatRoomId 메소드를 호출하고 받은 반환값을 반환한다.")
            void openChatRedisRepository_selectOpenChatListByChatRoomId_메소드를_호출하고_받은_반환값을_반환한다() {
                // given
                List<OpenChat> openChatList = List.of(new OpenChat(
                        1L,
                        1L,
                        1L,
                        "test",
                        1,
                        0,
                        null,
                        "2024-01-29 00:00:00"
                ));
                when(openChatRedisRepository.selectOpenChatListByChatRoomId(1L)).thenReturn(openChatList);

                // when
                List<OpenChat> result = openChatRedisService.getOpenChatListByOpenChatRoomId(1L);

                // then
                verify(openChatRedisRepository).selectOpenChatListByChatRoomId(1L);
                assertEquals(1, result.size());
            }
        }
        @Nested
        @DisplayName("getOpenChatListByOpenChatRoomIdAndOffsetAndLimit 메소드는")
        class Describe_getOpenChatListByOpenChatRoomIdAndOffsetAndLimit {
            @Test
            @DisplayName("openChatRedisRepository.selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit 메소드를 호출하고 받은 반환값을 반환한다.")
            void openChatRedisRepository_selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit_메소드를_호출하고_받은_반환값을_반환한다() {
                // given
                List<OpenChat> openChatList = List.of(new OpenChat(
                        1L,
                        1L,
                        1L,
                        "test",
                        1,
                        0,
                        null,
                        "2024-01-29 00:00:00"
                ));
                when(openChatRedisRepository.selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit(1L, 0, 1)).thenReturn(openChatList);

                // when
                List<OpenChat> result = openChatRedisService.getOpenChatListByOpenChatRoomIdAndOffsetAndLimit(1L, 0, 1);

                // then
                verify(openChatRedisRepository).selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit(1L, 0, 1);
                assertEquals(1, result.size());
            }
        }
        @Nested
        @DisplayName("createOpenChat 메소드는")
        class Describe_createOpenChat {
            @Test
            @DisplayName("openChatRedisRepository.insertOpenChat 메소드를 호출해서 채팅을 생성한다.")
            void openChatRedisRepository_insertOpenChat_메소드를_호출해서_채팅을_생성한다() {
                // given
                OpenChat openChat = new OpenChat(
                        1L,
                        1L,
                        1L,
                        "test",
                        1,
                        0,
                        null,
                        "2024-01-29 00:00:00"
                );

                // when
                openChatRedisService.createOpenChat(openChat);

                // then
                verify(openChatRedisRepository).insertOpenChat(openChat);
            }
        }
        @Nested
        @DisplayName("updateLastOpenChat 메소드는")
        class Describe_updateLastOpenChat {
            @Test
            @DisplayName("openChatRedisRepository.updateLastOpenChat 메소드를 호출해서 마지막 채팅을 업데이트한다.")
            void openChatRedisRepository_updateLastOpenChat_메소드를_호출해서_마지막_채팅을_업데이트한다() {
                // given
                OpenChat openChat = new OpenChat(
                        1L,
                        1L,
                        1L,
                        "test",
                        1,
                        0,
                        null,
                        "2024-01-29 00:00:00"
                );

                // when
                openChatRedisService.updateLastOpenChat(openChat);

                // then
                verify(openChatRedisRepository).updateLastOpenChat(openChat);
            }
        }
    }
}
