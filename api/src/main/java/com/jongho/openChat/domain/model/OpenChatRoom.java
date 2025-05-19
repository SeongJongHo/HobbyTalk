package com.jongho.openChat.domain.model;

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

    public OpenChatRoom(String title, String notice, Long managerId, Long categoryId, int maximumCapacity, String password) {
        this.title = title;
        this.notice = notice;
        this.managerId = managerId;
        this.categoryId = categoryId;
        this.maximumCapacity = maximumCapacity;
        this.currentAttendance = 0;
        this.password = password;
    }
}
