package com.jongho.openChat.application.service;

import com.jongho.openChat.application.repository.IOpenChatRoomMembershipRequestRepository;
import com.jongho.openChat.domain.model.OpenChatRoomMembershipRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenChatRoomMembershipRequestService {
    private final IOpenChatRoomMembershipRequestRepository IOpenChatRoomMembershipRequestRepository;

    public int countByRequesterIdAndStatus(Long requesterId, int status) {
        return IOpenChatRoomMembershipRequestRepository.countByRequesterIdAndStatus(requesterId, status);
    }

    public boolean existsByRequesterIdAndOpenChatRoomIdAndStatus(Long requesterId, Long openChatRoomId, int status) {
        return IOpenChatRoomMembershipRequestRepository.existsByRequesterIdAndOpenChatRoomIdAndStatus(requesterId, openChatRoomId, status);
    }

    public void createOpenChatRoomMembershipRequest(OpenChatRoomMembershipRequest openChatRoomMembershipRequest) {
        IOpenChatRoomMembershipRequestRepository.createOpenChatRoomMembershipRequest(openChatRoomMembershipRequest);
    }
}
