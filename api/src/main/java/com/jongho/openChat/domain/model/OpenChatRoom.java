package com.jongho.openChat.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class OpenChatRoom {
    private Long id;
    private final String title;
    private final String notice;
    private final Long managerId;
    private final Long categoryId;
    private final int maximumCapacity;
    private final int currentAttendance;
    private final String password;

    @JsonCreator
    public OpenChatRoom(
            @JsonProperty("title") String title,
            @JsonProperty("notice") String notice,
            @JsonProperty("managerId") Long managerId,
            @JsonProperty("categoryId") Long categoryId,
            @JsonProperty("maximumCapacity") int maximumCapacity,
            @JsonProperty("password") String password) {
        this.title = title;
        this.notice = notice;
        this.managerId = managerId;
        this.categoryId = categoryId;
        this.maximumCapacity = maximumCapacity;
        this.currentAttendance = 0;
        this.password = password;
    }
}
