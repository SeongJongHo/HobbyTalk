package com.jongho.openChat.application.dto.response;

import com.jongho.openChat.domain.model.OpenChatRoom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReadOpenChatRoomDto {

    private final Long id;
    private final String title;
    private final boolean isPrivate;
    private final int maximumCapacity;
    private final int currentAttendance;

    public static ReadOpenChatRoomDto of(OpenChatRoom openChatRoom) {
        return new ReadOpenChatRoomDto(
            openChatRoom.getId(),
            openChatRoom.getTitle(),
            openChatRoom.getPassword() != null,
            openChatRoom.getMaximumCapacity(),
            openChatRoom.getCurrentAttendance()
        );
    }
}
