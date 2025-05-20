package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChatRoomMembershipRequest;

public interface OpenChatRoomMembershipRequestService {
    int countByRequesterIdAndStatus(Long requesterId, int status);
    boolean existsByRequesterIdAndOpenChatRoomIdAndStatus(Long requesterId, Long openChatRoomId, int status);
    void createOpenChatRoomMembershipRequest(OpenChatRoomMembershipRequest openChatRoomMembershipRequest);
}
