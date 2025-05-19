package com.jongho.openChat.domain.model.redis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class CachedOpenChatRoom {
    private final Long id;
    private final String title;
    private final String notice;
    private final Long managerId;
    private final Long categoryId;
    private final int maximumCapacity;
    private final int currentAttendance;
    private final String createdTime;

    @JsonCreator
    public CachedOpenChatRoom(
            @JsonProperty("id") Long id,
            @JsonProperty("title") String title,
            @JsonProperty("notice") String notice,
            @JsonProperty("managerId") Long managerId,
            @JsonProperty("categoryId") Long categoryId,
            @JsonProperty("maximumCapacity") int maximumCapacity,
            @JsonProperty("currentAttendance") int currentAttendance,
            @JsonProperty("createdTime") String createdTime) {
        this.id = id;
        this.title = title;
        this.notice = notice;
        this.managerId = managerId;
        this.categoryId = categoryId;
        this.maximumCapacity = maximumCapacity;
        this.currentAttendance = currentAttendance;
        this.createdTime = createdTime;
    }
}
