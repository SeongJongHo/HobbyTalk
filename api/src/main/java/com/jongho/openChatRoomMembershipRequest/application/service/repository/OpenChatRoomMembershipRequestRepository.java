package com.jongho.openChatRoomMembershipRequest.application.service.repository;

import com.jongho.openChatRoomMembershipRequest.domain.model.OpenChatRoomMembershipRequest;

public interface OpenChatRoomMembershipRequestRepository {
    public int countByRequesterIdAndStatus(Long requesterId, int status);
    public boolean existsByRequesterIdAndOpenChatRoomIdAndStatus(Long requesterId, Long openChatRoomId, int status);
    public void createOpenChatRoomMembershipRequest(OpenChatRoomMembershipRequest openChatRoomMembershipRequest);
}
