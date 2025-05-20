package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChat;

import java.util.List;
import java.util.Optional;

public interface OpenChatRedisService {
    Optional<OpenChat> getLastOpenChatByOpenChatRoomId(Long openChatRoomId);
    List<OpenChat> getOpenChatListByOpenChatRoomId(Long openChatRoomId);
    List<OpenChat> getOpenChatListByOpenChatRoomIdAndOffsetAndLimit(Long openChatRoomId, int offset, int limit);
    void createOpenChat(OpenChat openChat);
    void updateLastOpenChat(OpenChat openChat);
}
