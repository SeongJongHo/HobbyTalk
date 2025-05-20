package com.jongho.openChat.application.facade;

import com.jongho.openChat.application.dto.response.OpenChatDto;

import java.util.List;

public interface ReadWebSocketOpenChatFacade {
    List<OpenChatDto> getInitialOpenChatList(Long openChatRoomId);
    List<OpenChatDto> getOpenChatListByOpenChatRoomIdAndLastCreatedTime(Long openChatRoomId, String lastCreatedTime);
}
