package com.jongho.openChat.application.repository;

import com.jongho.openChat.domain.model.OpenChat;

import java.util.List;
import java.util.Optional;

public interface IOpenChatRedisRepository {
    Optional<OpenChat> selectLastOpenChatByChatRoomId(Long openChatRoomId);
    List<OpenChat> selectOpenChatListByChatRoomId(Long openChatRoomId);
    List<OpenChat> selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit(Long openChatRoomId, int offset, int limit);
    void insertOpenChat(OpenChat openChat);
    void updateLastOpenChat(OpenChat openChat);
}
