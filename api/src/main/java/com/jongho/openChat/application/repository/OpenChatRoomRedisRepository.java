package com.jongho.openChat.application.repository;

import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoomConnectionInfo;

import java.util.List;
import java.util.Optional;

public interface OpenChatRoomRedisRepository {
    List<Long> getOpenChatRoomIdList(Long userId);
    void createOpenChatRoomUserList(Long openChatRoomId, List<Long> userIdList);
    void createOpenChatRoomUserList(Long openChatRoomId, Long userId);
    void createOpenChatRoomLastMessage(Long openChatRoomId, OpenChat openChat);
    void createRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId, CachedOpenChatRoomConnectionInfo cachedOpenChatRoomConnectionInfo);
    Optional<OpenChat> getLastOpenChatByChatRoomId(Long chatRoomId);
    List<Long> getOpenChatRoomUserList(Long openChatRoomId);
    CachedOpenChatRoomConnectionInfo getRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId);
    Optional<CachedOpenChatRoom> getOpenChatRoom(Long openChatRoomId);
    void updateInitUnreadChatCount(Long userId, Long openChatRoomId);
    void updateActiveChatRoom(Long userId, Long openChatRoomId, String activeFlag);
    void incrementUnreadMessageCount(Long userId, Long openChatRoomId);
    void updateLastExitTime(Long userId, Long openChatRoomId, String lastExitTime);
}
