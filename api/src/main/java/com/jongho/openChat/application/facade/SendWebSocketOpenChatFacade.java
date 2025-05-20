package com.jongho.openChat.application.facade;

import com.jongho.openChat.application.dto.response.OpenChatDto;

public interface SendWebSocketOpenChatFacade {
    void sendOpenChat(OpenChatDto openChatDto);
}
