package com.jongho.openChat.application.dto.response;

import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoomConnectionInfo;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@RequiredArgsConstructor
@AllArgsConstructor
public class OpenChatRoomDto {
    private final Long id;
    private final String title;
    private final String notice;
    private final Long managerId;
    private final Long categoryId;
    private final int maximumCapacity;
    private final int currentAttendance;
    private final String createdTime;
    private OpenChat lastChat;
    private CachedOpenChatRoomConnectionInfo cachedOpenChatRoomConnectionInfo;

    public OpenChatRoomDto(CachedOpenChatRoom cachedOpenChatRoom) {
        this.id = cachedOpenChatRoom.getId();
        this.title = cachedOpenChatRoom.getTitle();
        this.notice = cachedOpenChatRoom.getNotice();
        this.managerId = cachedOpenChatRoom.getManagerId();
        this.categoryId = cachedOpenChatRoom.getCategoryId();
        this.maximumCapacity = cachedOpenChatRoom.getMaximumCapacity();
        this.currentAttendance = cachedOpenChatRoom.getCurrentAttendance();
        this.createdTime = cachedOpenChatRoom.getCreatedTime();
    }

    public void setOpenChat(OpenChat lastChat) {
        this.lastChat = lastChat;
    }

    public void setCachedOpenChatRoomConnectionInfo(CachedOpenChatRoomConnectionInfo cachedOpenChatRoomConnectionInfo) {
        this.cachedOpenChatRoomConnectionInfo = cachedOpenChatRoomConnectionInfo;
    }
}
