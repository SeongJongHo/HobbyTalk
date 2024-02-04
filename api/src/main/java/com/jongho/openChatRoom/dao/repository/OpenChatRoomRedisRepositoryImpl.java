package com.jongho.openChatRoom.dao.repository;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChatRoom.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChatRoom.domain.model.redis.CachedOpenChatRoomConnectionInfo;
import com.jongho.openChatRoom.domain.repository.OpenChatRoomRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OpenChatRoomRedisRepositoryImpl implements OpenChatRoomRedisRepository {
    private final BaseRedisTemplate baseRedisTemplate;

    @Override
    public List<Long> getOpenChatRoomIdList(Long userId) {
        return baseRedisTemplate.getAllListData(RedisKeyGeneration.getJoinedChatRoomsKey(userId), Long.class);
    }

    @Override
    public void createOpenChatRoomUserList(Long openChatRoomId, List<Long> userIdList) {
        baseRedisTemplate.setAllListData(RedisKeyGeneration.getChatRoomUserListKey(openChatRoomId), userIdList);
    }

    @Override
    public void createOpenChatRoomUserList(Long openChatRoomId, Long userId) {
        baseRedisTemplate.setListData(RedisKeyGeneration.getChatRoomUserListKey(openChatRoomId), userId);
    }

    @Override
    public void createOpenChatRoomLastMessage(Long openChatRoomId, OpenChat openChat) {
        baseRedisTemplate.setListData(RedisKeyGeneration.getLastMessageKey(openChatRoomId), openChat);
    }

    @Override
    public void createRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId, CachedOpenChatRoomConnectionInfo cachedOpenChatRoomConnectionInfo) {
        baseRedisTemplate.setHashData(RedisKeyGeneration.getChatRoomConnectionInfoKey(userId, openChatRoomId), cachedOpenChatRoomConnectionInfo.toMap());
    }

    @Override
    public List<Long> getOpenChatRoomUserList(Long openChatRoomId) {
        return baseRedisTemplate.getAllListData(RedisKeyGeneration.getChatRoomUserListKey(openChatRoomId), Long.class);
    }

    @Override
    public CachedOpenChatRoomConnectionInfo getRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId) {
        return baseRedisTemplate.getHashData(RedisKeyGeneration.getChatRoomConnectionInfoKey(userId, openChatRoomId), CachedOpenChatRoomConnectionInfo.class);
    }

    @Override
    public Optional<OpenChat> getLastOpenChatByChatRoomId(Long chatRoomId){
        return Optional.ofNullable(
                baseRedisTemplate.getData(RedisKeyGeneration.getLastMessageKey(chatRoomId), OpenChat.class));
    }

    @Override
    public Optional<CachedOpenChatRoom> getOpenChatRoom(Long openChatRoomId){
        return Optional.ofNullable(
                baseRedisTemplate.getData(RedisKeyGeneration.getChatRoomKey(openChatRoomId), CachedOpenChatRoom.class));
    }
    @Override
    public void updateInitUnreadChatCount(Long userId, Long openChatRoomId){
        String UNREAD_CHAT_COUNT = "unReadMessageCount";
        String initCount = "0";
        baseRedisTemplate.setHashDataColumn(RedisKeyGeneration.getChatRoomConnectionInfoKey(userId, openChatRoomId), UNREAD_CHAT_COUNT, initCount);
    }
    @Override
    public void updateActiveChatRoom(Long userId, Long openChatRoomId, int activeFlag){
        String ACTIVE = "active";
        baseRedisTemplate.setHashDataColumn(RedisKeyGeneration.getChatRoomConnectionInfoKey(userId, openChatRoomId), ACTIVE, activeFlag);
    }
    @Override
    public void incrementUnreadMessageCount(Long userId, Long openChatRoomId){
        String UNREAD_CHAT_COUNT = "unReadMessageCount";
        int value = 1;
        baseRedisTemplate.incrementHashDataColumn(RedisKeyGeneration.getChatRoomConnectionInfoKey(userId, openChatRoomId), UNREAD_CHAT_COUNT, value);
    }
}
