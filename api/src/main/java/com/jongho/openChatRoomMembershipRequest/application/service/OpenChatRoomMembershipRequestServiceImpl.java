package com.jongho.openChatRoomMembershipRequest.application.service;

import com.jongho.openChatRoomMembershipRequest.domain.model.OpenChatRoomMembershipRequest;
import com.jongho.openChatRoomMembershipRequest.domain.repository.OpenChatRoomMembershipRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenChatRoomMembershipRequestServiceImpl implements OpenChatRoomMembershipRequestService {
    private final OpenChatRoomMembershipRequestRepository openChatRoomMembershipRequestRepository;

    @Override
    public int countByRequesterIdAndStatus(Long requesterId, int status) {
        return openChatRoomMembershipRequestRepository.countByRequesterIdAndStatus(requesterId, status);
    }

    @Override
    public boolean existsByRequesterIdAndOpenChatRoomIdAndStatus(Long requesterId, Long openChatRoomId, int status) {
        return openChatRoomMembershipRequestRepository.existsByRequesterIdAndOpenChatRoomIdAndStatus(requesterId, openChatRoomId, status);
    }

    @Override
    public void createOpenChatRoomMembershipRequest(OpenChatRoomMembershipRequest openChatRoomMembershipRequest) {
        openChatRoomMembershipRequestRepository.createOpenChatRoomMembershipRequest(openChatRoomMembershipRequest);
    }
}
