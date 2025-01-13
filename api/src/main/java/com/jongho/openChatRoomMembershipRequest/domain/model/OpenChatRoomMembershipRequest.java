package com.jongho.openChatRoomMembershipRequest.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OpenChatRoomMembershipRequest {
    private  Long id;
    private final Long requesterId;
    private final Long openChatRoomId;
    private final String message;
    private final int status;
    public void setId(Long id) {
        this.id = id;
    }
}
