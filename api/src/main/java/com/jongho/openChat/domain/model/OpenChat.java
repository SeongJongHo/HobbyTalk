package com.jongho.openChat.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OpenChat {
    private final Long id;
    private final Long senderId;
    private final Long openChatRoomId;
    private final Long SnowflakeId;
    private final String message;
    private final int type;
    private final int isDeleted;
    private final String deletedTime;
    private final String createdTime;

    @JsonCreator
    public OpenChat(
            @JsonProperty("id") Long id,
            @JsonProperty("senderId") Long senderId,
            @JsonProperty("openChatRoomId") Long openChatRoomId,
            @JsonProperty("snowflakeId") Long snowflakeId,
            @JsonProperty("message") String message,
            @JsonProperty("type") int type,
            @JsonProperty("isDeleted") int isDeleted,
            @JsonProperty("deletedTime") String deletedTime,
            @JsonProperty("createdTime") String createdTime) {
        this.id = id;
        this.senderId = senderId;
        this.openChatRoomId = openChatRoomId;
        this.SnowflakeId = snowflakeId;
        this.message = message;
        this.type = type;
        this.isDeleted = isDeleted;
        this.deletedTime = deletedTime;
        this.createdTime = createdTime;
    }
}