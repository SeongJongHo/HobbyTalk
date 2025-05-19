package com.jongho.openChat.application.facade;

import com.jongho.openChat.application.dto.response.OpenChatDto;

import java.util.List;

public interface ReadWebSocketOpenChatFacade {
    public List<OpenChatDto> getInitialOpenChatList(Long openChatRoomId);
    public List<OpenChatDto> getOpenChatListByOpenChatRoomIdAndLastCreatedTime(Long openChatRoomId, String lastCreatedTime);
}
