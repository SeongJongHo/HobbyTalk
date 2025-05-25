package com.jongho.openChat.application.service;

import com.jongho.common.util.date.DateUtil;
import com.jongho.openChat.application.repository.IOpenChatRoomRedisRepository;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoomConnectionInfo;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenChatRoomRedisService {
    private final IOpenChatRoomRedisRepository IOpenChatRoomRedisRepository;
    public List<Long> getOpenChatRoomIdList(Long userId) {
        return IOpenChatRoomRedisRepository.getOpenChatRoomIdList(userId);
    }
    public void createOpenChatRoomUserList(Long openChatRoomId, List<Long> userIdList) {
        IOpenChatRoomRedisRepository.createOpenChatRoomUserList(openChatRoomId, userIdList);
    }
    public void createOpenChatRoomUserList(Long openChatRoomId, Long userId) {
        IOpenChatRoomRedisRepository.createOpenChatRoomUserList(openChatRoomId, userId);
    }
    public void createOpenChatRoomLastMessage(Long openChatRoomId, OpenChat openChat) {
        IOpenChatRoomRedisRepository.createOpenChatRoomLastMessage(openChatRoomId, openChat);
    }
    public void createRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId, CachedOpenChatRoomConnectionInfo cachedOpenChatRoomConnectionInfo) {
        IOpenChatRoomRedisRepository.createRedisOpenChatRoomConnectionInfo(userId, openChatRoomId, cachedOpenChatRoomConnectionInfo);
    }
    public List<Long> getOpenChatRoomUserList(Long openChatRoomId) {
        return IOpenChatRoomRedisRepository.getOpenChatRoomUserList(openChatRoomId);
    }
    public CachedOpenChatRoomConnectionInfo getRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId) {
        return IOpenChatRoomRedisRepository.getRedisOpenChatRoomConnectionInfo(userId, openChatRoomId);
    }
    public Optional<OpenChat> getLastOpenChatByChatRoomId(Long chatRoomId){
        return IOpenChatRoomRedisRepository.getLastOpenChatByChatRoomId(chatRoomId);
    };
    public Optional<CachedOpenChatRoom> getOpenChatRoom(Long openChatRoomId){
        return IOpenChatRoomRedisRepository.getOpenChatRoom(openChatRoomId);
    };
    public void updateInitUnreadChatCount(Long userId, Long openChatRoomId){
        IOpenChatRoomRedisRepository.updateInitUnreadChatCount(userId, openChatRoomId);
    }
    public void updateActiveChatRoom(Long userId, Long openChatRoomId, String activeFlag){
        IOpenChatRoomRedisRepository.updateActiveChatRoom(userId, openChatRoomId, activeFlag);
    }
    public void incrementUnreadMessageCount(Long userId, Long openChatRoomId){
        IOpenChatRoomRedisRepository.incrementUnreadMessageCount(userId, openChatRoomId);
    }
    public void updateLastExitTime(Long userId, Long openChatRoomId){
        IOpenChatRoomRedisRepository.updateLastExitTime(userId, openChatRoomId, DateUtil.now());
    }
}
