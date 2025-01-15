package com.jongho.openChat.application.repository;

import com.jongho.openChat.domain.model.OpenChat;

import java.util.List;
import java.util.Optional;

public interface OpenChatRedisRepository {
    public Optional<OpenChat> selectLastOpenChatByChatRoomId(Long openChatRoomId);
    public List<OpenChat> selectOpenChatListByChatRoomId(Long openChatRoomId);
    public List<OpenChat> selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit(Long openChatRoomId, int offset, int limit);
    public void insertOpenChat(OpenChat openChat);
    public void updateLastOpenChat(OpenChat openChat);
}
