package com.jongho.openChatRoom.application.service;

import com.jongho.common.exception.OpenChatRoomNotFoundException;
import com.jongho.common.exception.UnAuthorizedException;
import com.jongho.openChat.application.service.OpenChatRoomService;
import com.jongho.openChat.domain.model.OpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChat.application.repository.IOpenChatRoomRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OpenChatRoomServiceImpl 클래스")
public class OpenChatRoomServiceTest {
    @Mock
    private IOpenChatRoomRepository IOpenChatRoomRepository;
    @InjectMocks
    private OpenChatRoomService openChatRoomService;

    @Nested
    @DisplayName("createOpenChatRoom 메소드는")
    class Describe_createOpenChatRoom {
        @Test
        @DisplayName("OpenChatRoomRepository의 createOpenChatRoom 메소드를 호출한다")
        void OpenChatRoomRepository의_createOpenChatRoom메소드를_한번_호출한다() {
            // given
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            doNothing().when(IOpenChatRoomRepository).createOpenChatRoom(openChatRoom);

            // when
            openChatRoomService.createOpenChatRoom(openChatRoom);

            // then
            verify(IOpenChatRoomRepository, times(1)).createOpenChatRoom(openChatRoom);
        }
    }

    @Nested
    @DisplayName("countByManagerId 메소드는")
    class Describe_countByManagerId {
        @Test
        @DisplayName("OpenChatRoomRepository의 countByManagerId 메소드를 호출해서 받은 count를 반환한다")
        void OpenChatRoomRepository의_countByManagerId메소드를_한번_호출해서_받은_count를_반환한다() {
            // given
            Long managerId = 1L;
            when(IOpenChatRoomRepository.countByManagerId(managerId)).thenReturn(5);

            // when
            int result = openChatRoomService.getOpenChatRoomCountByManagerId(managerId);

            // then
            verify(IOpenChatRoomRepository, times(1)).countByManagerId(managerId);
            assertEquals(5, result);
        }
    }

    @Nested
    @DisplayName("updateIncrementCurrentCapacity 메소드는")
    class Describe_updateIncrementCurrentCapacity {
        @Test
        @DisplayName("OpenChatRoomRepository의 updateIncrementCurrentCapacity 메소드를 호출한다")
        void OpenChatRoomRepository의_updateIncrementCurrentCapacity메소드를_한번_호출한다() {
            // given
            Long openChatRoomId = 1L;
            int currentAttendance = 1;
            doNothing().when(IOpenChatRoomRepository).updateIncrementCurrentCapacity(openChatRoomId, currentAttendance);

            // when
            openChatRoomService.incrementOpenChatRoomCurrentAttendance(openChatRoomId, currentAttendance);

            // then
            verify(IOpenChatRoomRepository, times(1)).updateIncrementCurrentCapacity(openChatRoomId, currentAttendance);
        }
    }

    @Nested
    @DisplayName("selectOneOpenChatRoomByManagerIdAndTitle 메소드는")
    class Describe_selectOneOpenChatRoomByManagerIdAndTitle {
        @Test
        @DisplayName("OpenChatRoomRepository의 selectOneOpenChatRoomByManagerIdAndTitle 메소드를 호출해서 받은 openChatRoom을 반환한다")
        void OpenChatRoomRepository의_selectOneOpenChatRoomByManagerIdAndTitle메소드를_한번_호출해서_받은_openChatRoom을_반환한다() {
            // given
            Long managerId = 1L;
            String title = "타이틀";
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            when(IOpenChatRoomRepository.selectOneOpenChatRoomByManagerIdAndTitle(managerId, title)).thenReturn(Optional.of(openChatRoom));

            // when
            OpenChatRoom result = openChatRoomService.getOpenChatRoomByManagerIdAndTitle(managerId, title).get();

            // then
            verify(IOpenChatRoomRepository, times(1)).selectOneOpenChatRoomByManagerIdAndTitle(managerId, title);
            assertEquals(openChatRoom, result);
        }
    }

    @Nested
    @DisplayName("selectOneOpenChatRoomByIdForUpdate 메소드는")
    class Describe_selectOneOpenChatRoomByIdForUpdate {
        @Test
        @DisplayName("OpenChatRoomRepository의 selectOneOpenChatRoomByIdForUpdate 메소드를 호출해서 받은 openChatRoom을 반환한다")
        void OpenChatRoomRepository의_selectOneOpenChatRoomByIdForUpdate메소드를_한번_호출해서_받은_openChatRoom을_반환한다() {
            // given
            Long openChatRoomId = 1L;
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            when(IOpenChatRoomRepository.selectOneOpenChatRoomByIdForUpdate(openChatRoomId)).thenReturn(Optional.of(openChatRoom));

            // when
            OpenChatRoom result = openChatRoomService.getOpenChatRoomByIdForUpdate(openChatRoomId).get();

            // then
            verify(IOpenChatRoomRepository, times(1)).selectOneOpenChatRoomByIdForUpdate(openChatRoomId);
            assertEquals(openChatRoom, result);
        }
    }

    @Nested
    @DisplayName("selectOneOpenChatRoomById 메소드는")
    class Describe_selectOneOpenChatRoomById {
        @Test
        @DisplayName("OpenChatRoomRepository의 selectOneOpenChatRoomById 메소드를 호출해서 받은 openChatRoom을 반환한다")
        void OpenChatRoomRepository의_selectOneOpenChatRoomById메소드를_한번_호출해서_받은_openChatRoom을_반환한다() {
            // given
            Long openChatRoomId = 1L;
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            when(IOpenChatRoomRepository.selectOneOpenChatRoomById(openChatRoomId)).thenReturn(Optional.of(openChatRoom));

            // when
            OpenChatRoom result = openChatRoomService.getOpenChatRoomById(openChatRoomId).get();

            // then
            verify(IOpenChatRoomRepository, times(1)).selectOneOpenChatRoomById(openChatRoomId);
            assertEquals(openChatRoom, result);
        }
    }

    @Nested
    @DisplayName("updateOpenChatRoomNotice 메소드는")
    class Describe_updateOpenChatRoomNotice {
        @Test
        @DisplayName("OpenChatRoomRepository의 updateOpenChatRoomNotice 메소드를 호출한다")
        void OpenChatRoomRepository의_updateOpenChatRoomNotice메소드를_한번_호출한다() {
            // given
            Long userId = 1L;
            Long openChatRoomId = 1L;
            String notice = "공지사항";
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            when(IOpenChatRoomRepository.selectOneOpenChatRoomById(openChatRoomId)).thenReturn(Optional.of(openChatRoom));
            doNothing().when(IOpenChatRoomRepository).updateOpenChatRoomNotice(openChatRoomId, notice);

            // when
            openChatRoomService.updateOpenChatRoomNotice(userId, openChatRoomId, notice);

            // then
            verify(IOpenChatRoomRepository, times(1)).selectOneOpenChatRoomById(openChatRoomId);
            verify(IOpenChatRoomRepository, times(1)).updateOpenChatRoomNotice(openChatRoomId, notice);
        }

        @Test
        @DisplayName("오픈채팅방이 존재하지 않으면 OpenChatRoomNotFoundException을 던진다")
        void 오픈채팅방이_존재하지_않으면_OpenChatRoomNotFoundException을_던진다() {
            // given
            Long userId = 1L;
            Long openChatRoomId = 1L;
            String notice = "공지사항";
            when(IOpenChatRoomRepository.selectOneOpenChatRoomById(openChatRoomId)).thenReturn(Optional.empty());

            // when
            // then
            assertThrows(OpenChatRoomNotFoundException.class, () -> openChatRoomService.updateOpenChatRoomNotice(userId, openChatRoomId, notice));
        }

        @Test
        @DisplayName("오픈채팅방의 매니저가 아니면 UnAuthorizedException을 던진다")
        void 오픈채팅방의_매니저가_아니면_UnAuthorizedException을_던진다() {
            // given
            Long userId = 1L;
            Long openChatRoomId = 1L;
            String notice = "공지사항";
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    2L,
                    1L,
                    200,
                    "비밀번호"
            );
            when(IOpenChatRoomRepository.selectOneOpenChatRoomById(openChatRoomId)).thenReturn(Optional.of(openChatRoom));

            // when
            // then
            assertThrows(UnAuthorizedException.class, () -> openChatRoomService.updateOpenChatRoomNotice(userId, openChatRoomId, notice));
        }
    }
    @Nested
    @DisplayName("getJoinOpenChatRoomList 메소드는")
    class Describe_getJoinOpenChatRoomList {
        @Test
        @DisplayName("OpenChatRoomRepository의 selectJoinOpenChatRoomByUserId 메소드를 호출해서 받은 openChatRoom을 반환한다")
        void OpenChatRoomRepository의_selectJoinOpenChatRoomByUserId메소드를_한번_호출해서_받은_redisOpenChatRoom을_반환한다() {
            // given
            Long userId = 1L;
            List<CachedOpenChatRoom> cachedOpenChatRooms = List.of(
                    new CachedOpenChatRoom(1L, "타이틀", "공지사항", 1L, 1L, 200, 1,"2024-01-30 12:34:56"),
                    new CachedOpenChatRoom(2L, "타이틀", "공지사항", 1L, 1L, 200, 2, "2024-01-30 12:34:56")
            );
            when(IOpenChatRoomRepository.selectJoinOpenChatRoomByUserId(userId)).thenReturn(cachedOpenChatRooms);

            // when
            openChatRoomService.getJoinOpenChatRoomList(userId);

            // then
            verify(IOpenChatRoomRepository, times(1)).selectJoinOpenChatRoomByUserId(userId);
        }
    }
    @Nested
    @DisplayName("getOpenChatRoomUserList 메소드는")
    class Describe_getOpenChatRoomUserList {
        @Test
        @DisplayName("OpenChatRoomRepository의 selectOpenChatRoomUserList 메소드를 호출해서 받은 openChatRoomUserList를 반환한다")
        void OpenChatRoomRepository의_selectOpenChatRoomUserList메소드를_한번_호출해서_받은_openChatRoomUserList를_반환한다() {
            // given
            Long openChatRoomId = 1L;
            List<Long> userIds = List.of(1L, 2L, 3L);
            when(IOpenChatRoomRepository.selectOpenChatRoomUser(openChatRoomId)).thenReturn(userIds);

            // when
            List<Long> result = openChatRoomService.getOpenChatRoomUserList(openChatRoomId);

            // then
            verify(IOpenChatRoomRepository, times(1)).selectOpenChatRoomUser(openChatRoomId);
            assertEquals(userIds, result);
        }
    }
    @Nested
    @DisplayName("getRedisOpenChatRoomById 메소드는")
    class Describe_getCachedOpenChatRoomById {
        @Test
        @DisplayName("OpenChatRoomRepository의 selectRedisOpenChatRoomById 메소드를 호출해서 받은 redisOpenChatRoom을 반환한다")
        void OpenChatRoomRepository의_selectRedisOpenChatRoomById메소드를_한번_호출해서_받은_redisOpenChatRoom을_반환한다() {
            // given
            Long openChatRoomId = 1L;
            CachedOpenChatRoom cachedOpenChatRoom = new CachedOpenChatRoom(1L, "타이틀", "공지사항", 1L, 1L, 200, 1,"2024-01-30 12:34:56");
            when(IOpenChatRoomRepository.selectRedisOpenChatRoomById(openChatRoomId)).thenReturn(Optional.of(cachedOpenChatRoom));

            // when
            CachedOpenChatRoom result = openChatRoomService.getRedisOpenChatRoomById(openChatRoomId).get();

            // then
            verify(IOpenChatRoomRepository, times(1)).selectRedisOpenChatRoomById(openChatRoomId);
            assertEquals(cachedOpenChatRoom, result);
        }
    }
}
