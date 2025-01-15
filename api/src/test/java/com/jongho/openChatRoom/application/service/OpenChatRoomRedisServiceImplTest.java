package com.jongho.openChatRoom.application.service;

import com.jongho.common.util.date.DateUtil;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChatRoom.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChatRoom.domain.model.redis.CachedOpenChatRoomConnectionInfo;
import com.jongho.openChatRoom.application.repository.OpenChatRoomRedisRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OpenChatRoomRedisServiceImpl 클래스")
public class OpenChatRoomRedisServiceImplTest {
    @Mock
    private OpenChatRoomRedisRepository openChatRoomRedisRepository;
    @InjectMocks
    private OpenChatRoomRedisServiceImpl openChatRoomRedisService;
    @Nested
    @DisplayName("getOpenChatRoomIdList 메서드는")
    class Describe_getOpenChatRoomIdList{
        @Test
        @DisplayName("openChatRoomRedisRepository.getOpenChatRoomIdList 메서드를 호출한다.")
        void openChatRoomRedisRepositoryd_getOpenChatRoomIdList_메서드를_호출하고_받은_값을_반환한다(){
            // given
            List<Long> openChatRoomIdList = List.of(1L, 2L, 3L);
            when(openChatRoomRedisRepository.getOpenChatRoomIdList(1L)).thenReturn(openChatRoomIdList);

            // when
            openChatRoomRedisService.getOpenChatRoomIdList(1L);

            // then
            verify(openChatRoomRedisRepository).getOpenChatRoomIdList(1L);
        }
    }
    @Nested
    @DisplayName("createOpenChatRoomUserList 메서드는")
    class Describe_createOpenChatRoomUserList{
        @Test
        @DisplayName("openChatRoomRedisRepository.createOpenChatRoomUserList 메서드를 호출한다.")
        void openChatRoomRedisRepositoryd_createOpenChatRoomUserList_메서드를_호출하고_받은_값을_반환한다(){
            // given
            List<Long> userIdList = List.of(1L, 2L, 3L);

            // when
            openChatRoomRedisService.createOpenChatRoomUserList(1L, userIdList);

            // then
            verify(openChatRoomRedisRepository).createOpenChatRoomUserList(1L, userIdList);
        }
    }
    @Nested
    @DisplayName("createOpenChatRoomLastMessage 메서드는")
    class Describe_createOpenChatRoomLastMessage{
        @Test
        @DisplayName("openChatRoomRedisRepository.createOpenChatRoomLastMessage 메서드를 호출한다.")
        void openChatRoomRedisRepositoryd_createOpenChatRoomLastMessage_메서드를_호출하고_받은_값을_반환한다(){
            // given

            // when
            openChatRoomRedisService.createOpenChatRoomLastMessage(1L, null);

            // then
            verify(openChatRoomRedisRepository).createOpenChatRoomLastMessage(1L, null);
        }
    }
    @Nested
    @DisplayName("createRedisOpenChatRoomConnectionInfo 메서드는")
    class Describe_createCachedOpenChatRoomConnectionInfo {
        @Test
        @DisplayName("openChatRoomRedisRepository.createRedisOpenChatRoomConnectionInfo 메서드를 호출한다.")
        void openChatRoomRedisRepositoryd_createRedisOpenChatRoomConnectionInfo_메서드를_호출하고_받은_값을_반환한다(){
            // given
            CachedOpenChatRoomConnectionInfo cachedOpenChatRoomConnectionInfo = new CachedOpenChatRoomConnectionInfo();
            // when
            openChatRoomRedisService.createRedisOpenChatRoomConnectionInfo(1L, 1L, cachedOpenChatRoomConnectionInfo);

            // then
            verify(openChatRoomRedisRepository).createRedisOpenChatRoomConnectionInfo(1L, 1L, cachedOpenChatRoomConnectionInfo);
        }
    }
    @Nested
    @DisplayName("getOpenChatRoomUserList 메서드는")
    class Describe_getOpenChatRoomUserList{
        @Test
        @DisplayName("openChatRoomRedisRepository.getOpenChatRoomUserList 메서드를 호출한다.")
        void openChatRoomRedisRepositoryd_getOpenChatRoomUserList_메서드를_호출하고_받은_값을_반환한다(){
            // given
            List<Long> userIdList = List.of(1L, 2L, 3L);
            when(openChatRoomRedisRepository.getOpenChatRoomUserList(1L)).thenReturn(userIdList);

            // when
            List<Long> result = openChatRoomRedisService.getOpenChatRoomUserList(1L);

            // then
            verify(openChatRoomRedisRepository).getOpenChatRoomUserList(1L);
            assertEquals(userIdList, result);
        }
    }
    @Nested
    @DisplayName("getRedisOpenChatRoomConnectionInfo 메서드는")
    class Describe_getCachedOpenChatRoomConnectionInfo {
        @Test
        @DisplayName("openChatRoomRedisRepository.getRedisOpenChatRoomConnectionInfo 메서드를 호출한다.")
        void openChatRoomRedisRepositoryd_getRedisOpenChatRoomConnectionInfo_메서드를_호출하고_받은_값을_반환한다(){
            // given
            CachedOpenChatRoomConnectionInfo cachedOpenChatRoomConnectionInfo = new CachedOpenChatRoomConnectionInfo();
            when(openChatRoomRedisRepository.getRedisOpenChatRoomConnectionInfo(1L, 1L)).thenReturn(cachedOpenChatRoomConnectionInfo);

            // when
            CachedOpenChatRoomConnectionInfo result = openChatRoomRedisService.getRedisOpenChatRoomConnectionInfo(1L, 1L);

            // then
            verify(openChatRoomRedisRepository).getRedisOpenChatRoomConnectionInfo(1L, 1L);
            assertEquals(cachedOpenChatRoomConnectionInfo, result);
        }
    }
    @Nested
    @DisplayName("getLastOpenChatByChatRoomId 메서드는")
    class Describe_getLastOpenChatByChatRoomId{
        @Test
        @DisplayName("openChatRoomRedisRepository.getLastOpenChatByChatRoomId 메서드를 호출한다.")
        void openChatRoomRedisRepositoryd_getLastOpenChatByChatRoomId_메서드를_호출하고_받은_값을_반환한다(){
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
            when(openChatRoomRedisRepository.getLastOpenChatByChatRoomId(1L)).thenReturn(openChat);

            // when
            Optional<OpenChat> result =openChatRoomRedisService.getLastOpenChatByChatRoomId(1L);

            // then
            verify(openChatRoomRedisRepository).getLastOpenChatByChatRoomId(1L);
            assertEquals(openChat, result);
        }
    }
    @Nested
    @DisplayName("getOpenChatRoom 메서드는")
    class Describe_getOpenChatRoom{
        @Test
        @DisplayName("openChatRoomRedisRepository.getOpenChatRoom 메서드를 호출한다.")
        void openChatRoomRedisRepositoryd_getOpenChatRoom_메서드를_호출하고_받은_값을_반환한다(){
            // given
            Optional<CachedOpenChatRoom> redisOpenChatRoom  = Optional.of(new CachedOpenChatRoom(
                    1L,
                    "title",
                    "notice",
                    1L,
                    1L,
                    200,
                    3,
                    "2024-01-29 00:00:00"
            ));
            when(openChatRoomRedisRepository.getOpenChatRoom(1L)).thenReturn(redisOpenChatRoom);

            // when
            Optional<CachedOpenChatRoom> result = openChatRoomRedisService.getOpenChatRoom(1L);

            // then
            verify(openChatRoomRedisRepository).getOpenChatRoom(1L);
            assertEquals(redisOpenChatRoom, result);
        }
    }
    @Nested
    @DisplayName("updateInitUnreadChatCount 메서드는")
    class Describe_updateInitUnreadChatCount{
        @Test
        @DisplayName("openChatRoomRedisRepository.updateInitUnreadChatCount 메서드를 호출해서 안읽은 메세지를 초기화시킨다.")
        void openChatRoomRedisRepositoryd_updateInitUnreadChatCount_메서드를_호출해서_안읽은_메세지를_초기화시킨다(){
            // given
            doNothing().when(openChatRoomRedisRepository).updateInitUnreadChatCount(1L, 1L);

            // when
            openChatRoomRedisService.updateInitUnreadChatCount(1L, 1L);

            // then
            verify(openChatRoomRedisRepository).updateInitUnreadChatCount(1L, 1L);
        }
    }
    @Nested
    @DisplayName("updateActiveChatRoom 메서드는")
    class Describe_updateActiveChatRoom{
        @Test
        @DisplayName("openChatRoomRedisRepository.updateActiveChatRoom 메서드를 호출해서 채팅방의 활성화 상태를 변경한다.")
        void openChatRoomRedisRepositoryd_updateActiveChatRoom_메서드를_호출해서_채팅방의_활성화_상태를_변경한다(){
            // given
            String ACTIVE = "1";
            doNothing().when(openChatRoomRedisRepository).updateActiveChatRoom(1L, 1L, ACTIVE);

            // when
            openChatRoomRedisService.updateActiveChatRoom(1L, 1L, ACTIVE);

            // then
            verify(openChatRoomRedisRepository).updateActiveChatRoom(1L, 1L, ACTIVE);
        }
    }
    @Nested
    @DisplayName("incrementUnreadMessageCount 메서드는")
    class Describe_incrementUnreadMessageCount{
        @Test
        @DisplayName("openChatRoomRedisRepository.incrementUnreadMessageCount 메서드를 호출해서 안읽은 메세지 수를 증가시킨다.")
        void openChatRoomRedisRepositoryd_incrementUnreadMessageCount_메서드를_호출해서_안읽은_메세지_수를_증가시킨다(){
            // given
            doNothing().when(openChatRoomRedisRepository).incrementUnreadMessageCount(1L, 1L);

            // when
            openChatRoomRedisService.incrementUnreadMessageCount(1L, 1L);

            // then
            verify(openChatRoomRedisRepository).incrementUnreadMessageCount(1L, 1L);
        }
    }
    @Nested
    @DisplayName("updateLastExitTime 메서드는")
    class Describe_updateLastExitTime{
        @Test
        @DisplayName("openChatRoomRedisRepository.updateLastExitTime 메서드를 호출해서 마지막 채팅방 나간 시간을 업데이트한다.")
        void openChatRoomRedisRepositoryd_updateLastExitTime_메서드를_호출해서_마지막_채팅방_나간_시간을_업데이트한다(){
            // given
            String LAST_EXIT_TIME = DateUtil.now();

            doNothing().when(openChatRoomRedisRepository).updateLastExitTime(1L, 1L, LAST_EXIT_TIME);

            // when
            openChatRoomRedisService.updateLastExitTime(1L, 1L);

            // then
            verify(openChatRoomRedisRepository).updateLastExitTime(1L, 1L, LAST_EXIT_TIME);
        }
    }
}
