package com.jongho.openChat.application.service;

import com.jongho.common.util.date.DateUtil;
import com.jongho.openChat.application.repository.OpenChatRoomRedisRepository;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoomConnectionInfo;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenChatRoomRedisServiceImpl {
    private final OpenChatRoomRedisRepository openChatRoomRedisRepository;
    public List<Long> getOpenChatRoomIdList(Long userId) {
        return openChatRoomRedisRepository.getOpenChatRoomIdList(userId);
    }
    public void createOpenChatRoomUserList(Long openChatRoomId, List<Long> userIdList) {
        openChatRoomRedisRepository.createOpenChatRoomUserList(openChatRoomId, userIdList);
    }
    public void createOpenChatRoomUserList(Long openChatRoomId, Long userId) {
        openChatRoomRedisRepository.createOpenChatRoomUserList(openChatRoomId, userId);
    }
    public void createOpenChatRoomLastMessage(Long openChatRoomId, OpenChat openChat) {
        openChatRoomRedisRepository.createOpenChatRoomLastMessage(openChatRoomId, openChat);
    }
    public void createRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId, CachedOpenChatRoomConnectionInfo cachedOpenChatRoomConnectionInfo) {
        openChatRoomRedisRepository.createRedisOpenChatRoomConnectionInfo(userId, openChatRoomId, cachedOpenChatRoomConnectionInfo);
    }
    public List<Long> getOpenChatRoomUserList(Long openChatRoomId) {
        return openChatRoomRedisRepository.getOpenChatRoomUserList(openChatRoomId);
    }
    public CachedOpenChatRoomConnectionInfo getRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId) {
        return openChatRoomRedisRepository.getRedisOpenChatRoomConnectionInfo(userId, openChatRoomId);
    }
    public Optional<OpenChat> getLastOpenChatByChatRoomId(Long chatRoomId){
        return openChatRoomRedisRepository.getLastOpenChatByChatRoomId(chatRoomId);
    };
    public Optional<CachedOpenChatRoom> getOpenChatRoom(Long openChatRoomId){
        return openChatRoomRedisRepository.getOpenChatRoom(openChatRoomId);
    };
    public void updateInitUnreadChatCount(Long userId, Long openChatRoomId){
        openChatRoomRedisRepository.updateInitUnreadChatCount(userId, openChatRoomId);
    }
    public void updateActiveChatRoom(Long userId, Long openChatRoomId, String activeFlag){
        openChatRoomRedisRepository.updateActiveChatRoom(userId, openChatRoomId, activeFlag);
    }
    public void incrementUnreadMessageCount(Long userId, Long openChatRoomId){
        openChatRoomRedisRepository.incrementUnreadMessageCount(userId, openChatRoomId);
    }
    public void updateLastExitTime(Long userId, Long openChatRoomId){
        openChatRoomRedisRepository.updateLastExitTime(userId, openChatRoomId, DateUtil.now());
    }
}
