package com.jongho.openChat.application.repository;

import com.jongho.openChat.domain.model.OpenChatRoomMembershipRequest;

public interface OpenChatRoomMembershipRequestRepository {
    int countByRequesterIdAndStatus(Long requesterId, int status);
    boolean existsByRequesterIdAndOpenChatRoomIdAndStatus(Long requesterId, Long openChatRoomId, int status);
    void createOpenChatRoomMembershipRequest(OpenChatRoomMembershipRequest openChatRoomMembershipRequest);
}
