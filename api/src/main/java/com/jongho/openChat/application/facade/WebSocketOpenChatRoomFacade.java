package com.jongho.openChat.application.facade;

import com.jongho.openChat.application.dto.response.OpenChatRoomDto;

import java.util.List;

public interface WebSocketOpenChatRoomFacade {
    List<OpenChatRoomDto> getOpenChatRoomList(Long userId);
}
