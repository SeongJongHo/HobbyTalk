package com.jongho.openChat.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OpenChatRoomMembershipRequestDto {
    private final String message;

    public OpenChatRoomMembershipRequestDto(@JsonProperty("message") String message) {
        this.message = null;
    }
}
