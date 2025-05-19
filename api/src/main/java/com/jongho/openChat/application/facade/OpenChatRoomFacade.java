package com.jongho.openChat.application.facade;

import com.jongho.openChat.application.dto.request.OpenChatRoomCreateDto;

public interface OpenChatRoomFacade {
    public void createOpenChatRoomAndOpenChatRoomUser(Long authUserId, OpenChatRoomCreateDto openChatRoomCreateDto);
    public void joinOpenChatRoom(Long authUserId, Long openChatRoomId, String password);
    public void createOpenChatRoomMembershipRequest(Long authUserId, Long openChatRoomId, String message);
}
