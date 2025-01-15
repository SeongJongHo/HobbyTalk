package com.jongho.openChatRoom.application.service;

import com.jongho.common.util.date.DateUtil;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChatRoom.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChatRoom.domain.model.redis.CachedOpenChatRoomConnectionInfo;
import com.jongho.openChatRoom.application.repository.OpenChatRoomRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenChatRoomRedisServiceImpl implements OpenChatRoomRedisService{
    private final OpenChatRoomRedisRepository openChatRoomRedisRepository;
    @Override
    public List<Long> getOpenChatRoomIdList(Long userId) {
        return openChatRoomRedisRepository.getOpenChatRoomIdList(userId);
    }
    @Override
    public void createOpenChatRoomUserList(Long openChatRoomId, List<Long> userIdList) {
        openChatRoomRedisRepository.createOpenChatRoomUserList(openChatRoomId, userIdList);
    }
    @Override
    public void createOpenChatRoomUserList(Long openChatRoomId, Long userId) {
        openChatRoomRedisRepository.createOpenChatRoomUserList(openChatRoomId, userId);
    }
    @Override
    public void createOpenChatRoomLastMessage(Long openChatRoomId, OpenChat openChat) {
        openChatRoomRedisRepository.createOpenChatRoomLastMessage(openChatRoomId, openChat);
    }
    @Override
    public void createRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId, CachedOpenChatRoomConnectionInfo cachedOpenChatRoomConnectionInfo) {
        openChatRoomRedisRepository.createRedisOpenChatRoomConnectionInfo(userId, openChatRoomId, cachedOpenChatRoomConnectionInfo);
    }
    @Override
    public List<Long> getOpenChatRoomUserList(Long openChatRoomId) {
        return openChatRoomRedisRepository.getOpenChatRoomUserList(openChatRoomId);
    }
    @Override
    public CachedOpenChatRoomConnectionInfo getRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId) {
        return openChatRoomRedisRepository.getRedisOpenChatRoomConnectionInfo(userId, openChatRoomId);
    }
    @Override
    public Optional<OpenChat> getLastOpenChatByChatRoomId(Long chatRoomId){
        return openChatRoomRedisRepository.getLastOpenChatByChatRoomId(chatRoomId);
    };
    @Override
    public Optional<CachedOpenChatRoom> getOpenChatRoom(Long openChatRoomId){
        return openChatRoomRedisRepository.getOpenChatRoom(openChatRoomId);
    };
    @Override
    public void updateInitUnreadChatCount(Long userId, Long openChatRoomId){
        openChatRoomRedisRepository.updateInitUnreadChatCount(userId, openChatRoomId);
    }
    @Override
    public void updateActiveChatRoom(Long userId, Long openChatRoomId, String activeFlag){
        openChatRoomRedisRepository.updateActiveChatRoom(userId, openChatRoomId, activeFlag);
    }
    @Override
    public void incrementUnreadMessageCount(Long userId, Long openChatRoomId){
        openChatRoomRedisRepository.incrementUnreadMessageCount(userId, openChatRoomId);
    }
    @Override
    public void updateLastExitTime(Long userId, Long openChatRoomId){
        openChatRoomRedisRepository.updateLastExitTime(userId, openChatRoomId, DateUtil.now());
    }
}
