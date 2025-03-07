package com.jongho.openChat.application.repository;

import com.jongho.openChat.application.dto.OpenChatDto;
import com.jongho.openChat.domain.model.OpenChat;

import java.util.List;
import java.util.Optional;

public interface OpenChatRepository {
    public Optional<OpenChat> selectLastOpenChatByChatRoomId(Long openChatRoomId);
    public int selectUnReadOpenChatCountByChatRoomIdAndLastExitTime(Long openChatRoomId, String lastExitTime, int limit);
    public List<OpenChatDto> selectOpenChatByChatRoomIdAndLastCreatedTime(Long openChatRoomId, String lastCreatedTime, int limit);
}
