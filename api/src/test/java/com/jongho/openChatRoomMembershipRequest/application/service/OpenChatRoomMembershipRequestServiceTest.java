package com.jongho.openChatRoomMembershipRequest.application.service;

import com.jongho.openChat.application.service.OpenChatRoomMembershipRequestService;
import com.jongho.openChat.common.enums.MembershipRequestStatusEnum;
import com.jongho.openChat.domain.model.OpenChatRoomMembershipRequest;
import com.jongho.openChat.application.repository.IOpenChatRoomMembershipRequestRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("OpenChatRoomMembershipRequestServiceImpl 클래스")
public class OpenChatRoomMembershipRequestServiceTest {
    @Mock
    private IOpenChatRoomMembershipRequestRepository IOpenChatRoomMembershipRequestRepository;
    @InjectMocks
    private OpenChatRoomMembershipRequestService openChatRoomMembershipRequestService;

    @Nested
    @DisplayName("createOpenChatRoomMembershipRequest 메소드는")
    class Describe_createOpenChatRoomMembershipRequest {
        @Test
        @DisplayName("OpenChatRoomMembershipRequestRepository의 createOpenChatRoomMembershipRequest 메소드를 호출한다")
        void OpenChatRoomMembershipRequestRepository의_createOpenChatRoomMembershipRequest메소드를_한번_호출한다() {
            // given
            OpenChatRoomMembershipRequest openChatRoomMembershipRequest = new OpenChatRoomMembershipRequest(
                    1L
                    , 1L
                    , "참가요청"
                    , MembershipRequestStatusEnum.PARTICIPATION_REQUEST.getValue()
            );
            doNothing().when(IOpenChatRoomMembershipRequestRepository).createOpenChatRoomMembershipRequest(openChatRoomMembershipRequest);

            // when
            openChatRoomMembershipRequestService.createOpenChatRoomMembershipRequest(openChatRoomMembershipRequest);

            // then
            verify(IOpenChatRoomMembershipRequestRepository, times(1)).createOpenChatRoomMembershipRequest(openChatRoomMembershipRequest);
        }
    }

    @Nested
    @DisplayName("countByRequesterIdAndStatus 메소드는")
    class Describe_countByRequesterIdAndStatus {
        @Test
        @DisplayName("OpenChatRoomMembershipRequestRepository의 countByRequesterIdAndStatus 메소드를 호출해서 받은 count를 반환한다")
        void OpenChatRoomMembershipRequestRepository의_countByRequesterIdAndStatus메소드를_한번_호출해서_받은_count를_반환한다() {
            // given
            Long requesterId = 1L;
            int status = 1;
            when(IOpenChatRoomMembershipRequestRepository.countByRequesterIdAndStatus(requesterId, status)).thenReturn(5);

            // when
            int count = openChatRoomMembershipRequestService.countByRequesterIdAndStatus(requesterId, status);

            // then
            verify(IOpenChatRoomMembershipRequestRepository, times(1)).countByRequesterIdAndStatus(requesterId, status);
            assertEquals(count, 5);
        }
    }

    @Nested
    @DisplayName("existsByRequesterIdAndOpenChatRoomIdAndStatus 메소드는")
    class Describe_existsByRequesterIdAndOpenChatRoomIdAndStatus {
        @Test
        @DisplayName("OpenChatRoomMembershipRequestRepository의 existsByRequesterIdAndOpenChatRoomIdAndStatus 메소드를 호출해서 받은 boolean을 반환한다")
        void OpenChatRoomMembershipRequestRepository의_existsByRequesterIdAndOpenChatRoomIdAndStatus메소드를_한번_호출해서_받은_boolean을_반환한다() {
            // given
            Long requesterId = 1L;
            Long openChatRoomId = 1L;
            int status = 1;
            when(IOpenChatRoomMembershipRequestRepository.existsByRequesterIdAndOpenChatRoomIdAndStatus(requesterId, openChatRoomId, status)).thenReturn(true);

            // when
            boolean result = openChatRoomMembershipRequestService.existsByRequesterIdAndOpenChatRoomIdAndStatus(requesterId, openChatRoomId, status);

            // then
            verify(IOpenChatRoomMembershipRequestRepository, times(1)).existsByRequesterIdAndOpenChatRoomIdAndStatus(requesterId, openChatRoomId, status);
        }
    }
}
