package com.jongho.openChatRoomMembershipRequest.application.service;

import com.jongho.openChatRoomMembershipRequest.domain.model.OpenChatRoomMembershipRequest;

public interface OpenChatRoomMembershipRequestService {
    public int countByRequesterIdAndStatus(Long requesterId, int status);
    public boolean existsByRequesterIdAndOpenChatRoomIdAndStatus(Long requesterId, Long openChatRoomId, int status);
    public void createOpenChatRoomMembershipRequest(OpenChatRoomMembershipRequest openChatRoomMembershipRequest);
}
