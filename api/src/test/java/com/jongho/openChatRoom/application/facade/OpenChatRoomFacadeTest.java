package com.jongho.openChatRoom.application.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jongho.category.application.service.CategoryService;
import com.jongho.category.domain.model.Category;
import com.jongho.common.exception.AlreadyExistsException;
import com.jongho.common.exception.CategoryNotFoundException;
import com.jongho.common.exception.InvalidPasswordException;
import com.jongho.common.exception.MaxChatRoomsExceededException;
import com.jongho.common.exception.MaxChatRoomsJoinException;
import com.jongho.common.exception.OpenChatRoomNotFoundException;
import com.jongho.openChat.application.dto.request.OpenChatRoomCreateDto;
import com.jongho.openChat.application.facade.OpenChatRoomFacade;
import com.jongho.openChat.application.service.OpenChatRoomMembershipRequestService;
import com.jongho.openChat.application.service.OpenChatRoomService;
import com.jongho.openChat.application.service.OpenChatRoomUserService;
import com.jongho.openChat.domain.model.OpenChatRoom;
import com.jongho.openChat.domain.model.OpenChatRoomUser;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("OpenChatRoomFacadeImpl 클래스")
public class OpenChatRoomFacadeTest {
    @Mock
    private OpenChatRoomService openChatRoomService;
    @Mock
    private OpenChatRoomUserService openChatRoomUserService;
    @Mock
    private OpenChatRoomMembershipRequestService openChatRoomMembershipRequestService;
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private OpenChatRoomFacade openChatRoomFacade;

    @Nested
    @DisplayName("createOpenChatRoom 메소드는")
    class Describe_createOpenChatRoom {
        private Long userId;
        private OpenChatRoomCreateDto openChatRoomCreateDto;
        private OpenChatRoom openChatRoom;
        @BeforeEach
        void setUp() {
            userId = 1L;
            openChatRoomCreateDto = new OpenChatRoomCreateDto(
                    "타이틀",
                    "공지사항",
                    1L,
                    200,
                    "비밀번호"
            );
            openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
        }

        @Test
        @DisplayName("생성하려는 유저가 소유하고있는 챗룸이 5개 이상이면 MaxChatRoomsExceededException예외를 던진다")
        void 생성하려는_유저가_소유하고있는_챗룸이_5개_이상이면_MaxChatRoomsExceededException예외를_던진다() {
            // given
            when(openChatRoomService.getOpenChatRoomCountByManagerId(userId)).thenReturn(5);

            // when then
            assertThrows(MaxChatRoomsExceededException.class, () -> {
                openChatRoomFacade.createOpenChatRoomAndOpenChatRoomUser(userId, openChatRoomCreateDto);});
        }

        @Test
        @DisplayName("생성하려는 챗룸의 카테고리가 존재하지 않으면 CategoryNotFoundException예외를 던진다")
        void 생성하려는_챗룸의_카테고리가_존재하지_않으면_CategoryNotFoundException예외를_던진다() {
            // given
            when(openChatRoomService.getOpenChatRoomCountByManagerId(userId)).thenReturn(4);
            when(categoryService.getOneCategoryById(openChatRoomCreateDto.getCategoryId())).thenReturn(Optional.empty());

            // when then
            assertThrows(CategoryNotFoundException.class, () -> {
                openChatRoomFacade.createOpenChatRoomAndOpenChatRoomUser(userId, openChatRoomCreateDto);});
        }

        @Test
        @DisplayName("생성하려는 챗룸의 카테고리가 존재하고, 유저가 소유하고있는 챗룸이 5개 이하이면서, 이미 존재하는 챗룸이면 AlreadyExistsException예외를 던진다")
        void 생성하려는_챗룸의_카테고리가_존재하고_유저가_소유하고있는_챗룸이_5개_이하이면서_이미_존재하는_챗룸이면_AlreadyExistsException예외를_던진다() {
            // given
            when(openChatRoomService.getOpenChatRoomCountByManagerId(userId)).thenReturn(4);
            when(categoryService.getOneCategoryById(openChatRoomCreateDto.getCategoryId())).thenReturn(Optional.of(new Category("카테고리이름", 1L)));
            when(openChatRoomService.getOpenChatRoomByManagerIdAndTitle(userId, openChatRoomCreateDto.getTitle())).thenReturn(Optional.of(openChatRoom));

            // when then
            Exception e = assertThrows(AlreadyExistsException.class, () -> {
                openChatRoomFacade.createOpenChatRoomAndOpenChatRoomUser(userId, openChatRoomCreateDto);});
            assertEquals("이미 존재하는 채팅방입니다.", e.getMessage());
        }

        @Test
        @DisplayName("생성하려는 챗룸의 카테고리가 존재하고, 유저가 소유하고있는 챗룸이 5개 이하이면 챗룸을 생성하고, 챗룸유저를 생성한다")
        void 생성하려는_챗룸의_카테고리가_존재하고_유저가_소유하고있는_챗룸이_5개_이하이면_챗룸을_생성하고_챗룸유저를_생성한다() {
            // given
            when(openChatRoomService.getOpenChatRoomCountByManagerId(userId)).thenReturn(4);
            when(categoryService.getOneCategoryById(openChatRoomCreateDto.getCategoryId())).thenReturn(Optional.of(new Category("카테고리이름", 1L)));
            when(openChatRoomService.getOpenChatRoomByManagerIdAndTitle(userId, openChatRoomCreateDto.getTitle())).thenReturn(Optional.empty());
            doNothing().when(openChatRoomService).createOpenChatRoom(openChatRoom);


            // when
            openChatRoomFacade.createOpenChatRoomAndOpenChatRoomUser(userId, openChatRoomCreateDto);

            // then
            verify(openChatRoomService, times(1)).createOpenChatRoom(openChatRoom);
            verify(openChatRoomUserService, times(1)).createOpenChatRoomUser(any());
        }
    }

    @Nested
    @DisplayName("joinOpenChatRoom 메소드는")
    class Describe_joinOpenChatRoom {
        private Long userId;
        private Long openChatRoomId;
        private String password;
        private OpenChatRoom openChatRoom;

        @BeforeEach
        void setUp() {
            userId = 1L;
            openChatRoomId = 1L;
            password = "비밀번호";
            openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
        }

        @Test
        @DisplayName("입장하려는 챗룸이 존재하지 않으면 OpenChatRoomNotFoundException예외를 던진다")
        void 입장하려는_챗룸이_존재하지_않으면_OpenChatRoomNotFoundException예외를_던진다() {
            // given
            when(openChatRoomService.getOpenChatRoomByIdForUpdate(openChatRoomId)).thenReturn(Optional.empty());

            // when then
            assertThrows(OpenChatRoomNotFoundException.class, () -> {
                openChatRoomFacade.joinOpenChatRoom(userId, openChatRoomId, password);
            });
        }

        @Test
        @DisplayName("입장하려는 챗룸의 비밀번호가 일치하지 않으면 InvalidPasswordException예외를 던진다")
        void 입장하려는_챗룸의_비밀번호가_일치하지_않으면_InvalidPasswordException예외를_던진다() {
            // given
            password = "비밀번호1";
            when(openChatRoomService.getOpenChatRoomByIdForUpdate(openChatRoomId)).thenReturn(Optional.of(openChatRoom));

            // when then
            assertThrows(InvalidPasswordException.class, () -> {
                openChatRoomFacade.joinOpenChatRoom(userId, openChatRoomId, password);
            });
        }

        @Test
        @DisplayName("입장하려는 챗룸의 최대인원을 초과하면 MaxChatRoomsJoinException예외를 던진다")
        void 입장하려는_챗룸의_최대인원을_초과하면_MaxChatRooms_JoinException예외를_던진다() {
            // given
            openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    1,
                    1,
                    "비밀번호"
            );
            when(openChatRoomService.getOpenChatRoomByIdForUpdate(openChatRoomId)).thenReturn(Optional.of(openChatRoom));

            // when then
            assertThrows(MaxChatRoomsJoinException.class, () -> {
                openChatRoomFacade.joinOpenChatRoom(userId, openChatRoomId, password);
            });
        }

        @Test
        @DisplayName("이미 참여중인 챗룸이면 AlreadyExistsException예외를 던진다")
        void 이미_참여중인_챗룸이면_AlreadyExistsException예외를_던진다() {
            // given
            when(openChatRoomService.getOpenChatRoomByIdForUpdate(openChatRoomId)).thenReturn(Optional.of(openChatRoom));
            when(openChatRoomUserService.getOpenChatRoomUserByOpenChatRoomIdAndUserId(openChatRoomId, userId)).thenReturn(Optional.of(new OpenChatRoomUser(openChatRoomId, userId)));

            // when then
            assertThrows(AlreadyExistsException.class, () -> {
                openChatRoomFacade.joinOpenChatRoom(userId, openChatRoomId, password);
            });
        }

        @Test
        @DisplayName("모든 밸리데이션 검사가 끝나고 문제가 없으면 챗룸에 참여하고, 챗룸의 현재인원을 증가시킨다")
        void 모든_밸리데이션_검사가_끝나고_문제가_없으면_챗룸에_참여하고_챗룸의_현재인원을_증가시킨다() {
            // given
            when(openChatRoomService.getOpenChatRoomByIdForUpdate(openChatRoomId)).thenReturn(Optional.of(openChatRoom));
            when(openChatRoomUserService.getOpenChatRoomUserByOpenChatRoomIdAndUserId(openChatRoomId, userId)).thenReturn(Optional.empty());
            doNothing().when(openChatRoomUserService).createOpenChatRoomUser(any());
            doNothing().when(openChatRoomService).incrementOpenChatRoomCurrentAttendance(openChatRoomId, openChatRoom.getCurrentAttendance());

            // when
            openChatRoomFacade.joinOpenChatRoom(userId, openChatRoomId, password);

            // then
            verify(openChatRoomUserService, times(1)).createOpenChatRoomUser(any());
            verify(openChatRoomService, times(1)).incrementOpenChatRoomCurrentAttendance(openChatRoomId, openChatRoom.getCurrentAttendance());
        }
    }

    @Nested
    @DisplayName("createOpenChatRoomMembershipRequest 메소드는")
    class Describe_createOpenChatRoomMembershipRequest {
        private Long userId;
        private Long openChatRoomId;
        private String message;
        private OpenChatRoom openChatRoom;

        @BeforeEach
        void setUp() {
            userId = 1L;
            openChatRoomId = 1L;
            message = "메시지";
            openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
        }

        @Test
        @DisplayName("입장하려는 챗룸이 존재하지 않으면 OpenChatRoonNotFoundException예외를 던진다")
        void 입장하려는_챗룸이_존재하지_않으면_OpenChatRoonNotFoundException예외를_던진다() {
            // given
            when(openChatRoomService.getOpenChatRoomById(openChatRoomId)).thenReturn(Optional.empty());

            // when then
            assertThrows(OpenChatRoomNotFoundException.class, () -> {
                openChatRoomFacade.createOpenChatRoomMembershipRequest(userId, openChatRoomId, message);
            });
        }

        @Test
        @DisplayName("입장하려는 챗룸의 최대인원을 초과하면 MaxChatRoomsJoinException예외를 던진다")
        void 입장하려는_챗룸의_최대인원을_초과하면_MaxChatRooms_JoinException예외를_던진다() {
            // given
            openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    1,
                    1,
                    "비밀번호"
            );
            when(openChatRoomService.getOpenChatRoomById(openChatRoomId)).thenReturn(Optional.of(openChatRoom));

            // when then
            assertThrows(MaxChatRoomsJoinException.class, () -> {
                openChatRoomFacade.createOpenChatRoomMembershipRequest(userId, openChatRoomId, message);
            });
        }

        @Test
        @DisplayName("이미 참여신청한 챗룸이면 AlreadyExistsException예외를 던진다")
        void 이미_참여신청한_챗룸이면_AlreadyExistsException예외를_던진다() {
            // given
            when(openChatRoomService.getOpenChatRoomById(openChatRoomId)).thenReturn(Optional.of(openChatRoom));
            when(openChatRoomMembershipRequestService.existsByRequesterIdAndOpenChatRoomIdAndStatus(userId, openChatRoomId, 1)).thenReturn(true);

            // when then
            assertThrows(AlreadyExistsException.class, () -> {
                openChatRoomFacade.createOpenChatRoomMembershipRequest(userId, openChatRoomId, message);
            });
        }

        @Test
        @DisplayName("이미 참여중인 챗룸이면 AlreadyExistsException예외를 던진다")
        void 이미_참여중인_챗룸이면_AlreadyExistsException예외를_던진다() {
            // given
            when(openChatRoomService.getOpenChatRoomById(openChatRoomId)).thenReturn(Optional.of(openChatRoom));
            when(openChatRoomMembershipRequestService.existsByRequesterIdAndOpenChatRoomIdAndStatus(userId, openChatRoomId, 1)).thenReturn(false);
            when(openChatRoomUserService.getOpenChatRoomUserByOpenChatRoomIdAndUserId(openChatRoomId, userId)).thenReturn(Optional.of(new OpenChatRoomUser(openChatRoomId, userId)));

            // when then
            assertThrows(AlreadyExistsException.class, () -> {
                openChatRoomFacade.createOpenChatRoomMembershipRequest(userId, openChatRoomId, message);
            });
        }

        @Test
        @DisplayName("최대 5개의 챗룸에 참여신청 할 수 있고, 문제가 없으면 참여신청을 생성한다")
        void 최대_5개의_챗룸에_참여신청_할_수_있고_문제가_없으면_참여신청을_생성한다() {
            // given
            when(openChatRoomService.getOpenChatRoomById(openChatRoomId)).thenReturn(Optional.of(openChatRoom));
            when(openChatRoomMembershipRequestService.existsByRequesterIdAndOpenChatRoomIdAndStatus(userId, openChatRoomId, 1)).thenReturn(false);
            when(openChatRoomUserService.getOpenChatRoomUserByOpenChatRoomIdAndUserId(openChatRoomId, userId)).thenReturn(Optional.empty());
            when(openChatRoomMembershipRequestService.countByRequesterIdAndStatus(userId, 1)).thenReturn(4);
            doNothing().when(openChatRoomMembershipRequestService).createOpenChatRoomMembershipRequest(any());

            // when
            openChatRoomFacade.createOpenChatRoomMembershipRequest(userId, openChatRoomId, message);

            // then
            verify(openChatRoomMembershipRequestService, times(1)).createOpenChatRoomMembershipRequest(any());
        }
    }
}
