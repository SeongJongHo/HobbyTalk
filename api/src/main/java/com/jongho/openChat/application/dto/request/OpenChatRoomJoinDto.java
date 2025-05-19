package com.jongho.openChat.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OpenChatRoomJoinDto {
    @Nullable
    private final String password;

    public OpenChatRoomJoinDto(@JsonProperty("password") String password) {
        this.password = password;
    }
}
