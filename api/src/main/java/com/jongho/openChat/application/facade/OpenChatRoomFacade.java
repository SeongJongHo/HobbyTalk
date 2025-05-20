package com.jongho.openChat.application.facade;

import com.jongho.openChat.application.dto.request.OpenChatRoomCreateDto;

public interface OpenChatRoomFacade {
    void createOpenChatRoomAndOpenChatRoomUser(Long authUserId, OpenChatRoomCreateDto openChatRoomCreateDto);
    void joinOpenChatRoom(Long authUserId, Long openChatRoomId, String password);
    void createOpenChatRoomMembershipRequest(Long authUserId, Long openChatRoomId, String message);
}
