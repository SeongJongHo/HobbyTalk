package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChatRoomMembershipRequest;

public interface OpenChatRoomMembershipRequestService {
    public int countByRequesterIdAndStatus(Long requesterId, int status);
    public boolean existsByRequesterIdAndOpenChatRoomIdAndStatus(Long requesterId, Long openChatRoomId, int status);
    public void createOpenChatRoomMembershipRequest(OpenChatRoomMembershipRequest openChatRoomMembershipRequest);
}
