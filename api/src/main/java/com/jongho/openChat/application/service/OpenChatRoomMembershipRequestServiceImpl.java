package com.jongho.openChat.application.service;

import com.jongho.openChat.application.repository.OpenChatRoomMembershipRequestRepository;
import com.jongho.openChat.domain.model.OpenChatRoomMembershipRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenChatRoomMembershipRequestServiceImpl {
    private final OpenChatRoomMembershipRequestRepository openChatRoomMembershipRequestRepository;

    public int countByRequesterIdAndStatus(Long requesterId, int status) {
        return openChatRoomMembershipRequestRepository.countByRequesterIdAndStatus(requesterId, status);
    }

    public boolean existsByRequesterIdAndOpenChatRoomIdAndStatus(Long requesterId, Long openChatRoomId, int status) {
        return openChatRoomMembershipRequestRepository.existsByRequesterIdAndOpenChatRoomIdAndStatus(requesterId, openChatRoomId, status);
    }

    public void createOpenChatRoomMembershipRequest(OpenChatRoomMembershipRequest openChatRoomMembershipRequest) {
        openChatRoomMembershipRequestRepository.createOpenChatRoomMembershipRequest(openChatRoomMembershipRequest);
    }
}
