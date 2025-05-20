package com.jongho.openChat.application.service;

import com.jongho.openChat.application.dto.response.OpenChatDto;
import com.jongho.openChat.domain.model.OpenChat;

import java.util.List;
import java.util.Optional;

public interface OpenChatService {
    Optional<OpenChat> getLastOpenChatByOpenChatRoomId(Long openChatRoomId);
    int getUnReadOpenChatCountByOpenChatRoomIdAndLastExitTime(Long openChatRoomId, String lastExitTime, int limit);
    List<OpenChatDto> getOpenChatByOpenChatRoomIdAndLastCreatedTime(Long openChatRoomId, String lastCreatedTime, int limit);
}
