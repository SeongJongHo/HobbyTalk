package com.jongho.openChat.application.repository;

import com.jongho.openChat.application.dto.response.OpenChatDto;
import com.jongho.openChat.domain.model.OpenChat;

import java.util.List;
import java.util.Optional;

public interface OpenChatRepository {
    Optional<OpenChat> selectLastOpenChatByChatRoomId(Long openChatRoomId);
    int selectUnReadOpenChatCountByChatRoomIdAndLastExitTime(Long openChatRoomId, String lastExitTime, int limit);
    List<OpenChatDto> selectOpenChatByChatRoomIdAndLastCreatedTime(Long openChatRoomId, String lastCreatedTime, int limit);
}
