package com.jongho.openChat.infra.cache;

import static com.jongho.openChat.common.enums.ConnectionInfoFieldEnum.ACTIVE;
import static com.jongho.openChat.common.enums.ConnectionInfoFieldEnum.LAST_EXIT_TIME;
import static com.jongho.openChat.common.enums.ConnectionInfoFieldEnum.UN_READ_MESSAGE_COUNT;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import com.jongho.openChat.application.repository.IOpenChatRoomRedisRepository;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoomConnectionInfo;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpenChatRoomRedisRepository implements IOpenChatRoomRedisRepository {
    private final BaseRedisTemplate baseRedisTemplate;

    @Override
    public List<Long> getOpenChatRoomIdList(Long userId) {
        return baseRedisTemplate.getList(
            RedisKeyGeneration.getJoinedChatRoomsKey(userId),
            Long.class,
            0,
            -1);
    }

    @Override
    public void createOpenChatRoomUserList(Long openChatRoomId, List<Long> userIdList) {
        baseRedisTemplate.rPushList(
            RedisKeyGeneration.getChatRoomUserListKey(openChatRoomId),
            userIdList);
    }

    @Override
    public void createOpenChatRoomUserList(Long openChatRoomId, Long userId) {
        baseRedisTemplate.rPushList(RedisKeyGeneration.getChatRoomUserListKey(openChatRoomId),
            userId);
    }

    @Override
    public void createOpenChatRoomLastMessage(Long openChatRoomId, OpenChat openChat) {
        baseRedisTemplate.rPushList(RedisKeyGeneration.getLastMessageKey(openChatRoomId), openChat);
    }

    @Override
    public void createRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId, CachedOpenChatRoomConnectionInfo cachedOpenChatRoomConnectionInfo) {
        baseRedisTemplate.putHash(
            RedisKeyGeneration.getChatRoomConnectionInfoKey(userId, openChatRoomId),
            cachedOpenChatRoomConnectionInfo.toMap());
    }

    @Override
    public List<Long> getOpenChatRoomUserList(Long openChatRoomId) {
        return baseRedisTemplate.getList(RedisKeyGeneration.getChatRoomUserListKey(openChatRoomId),
            Long.class, 0, -1);
    }

    @Override
    public CachedOpenChatRoomConnectionInfo getRedisOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId) {
        return baseRedisTemplate.getHash(
            RedisKeyGeneration.getChatRoomConnectionInfoKey(userId, openChatRoomId),
            CachedOpenChatRoomConnectionInfo.class);
    }

    @Override
    public Optional<OpenChat> getLastOpenChatByChatRoomId(Long chatRoomId){
        return Optional.ofNullable(
                baseRedisTemplate.get(RedisKeyGeneration.getLastMessageKey(chatRoomId), OpenChat.class));
    }

    @Override
    public Optional<CachedOpenChatRoom> getOpenChatRoom(Long openChatRoomId){
        return Optional.ofNullable(
                baseRedisTemplate.get(RedisKeyGeneration.getChatRoomKey(openChatRoomId), CachedOpenChatRoom.class));
    }
    @Override
    public void updateInitUnreadChatCount(Long userId, Long openChatRoomId){
        String UNREAD_CHAT_COUNT = "unReadMessageCount";
        String initCount = "0";
        baseRedisTemplate.putHash(
            RedisKeyGeneration.getChatRoomConnectionInfoKey(userId, openChatRoomId),
            UNREAD_CHAT_COUNT, initCount);
    }
    @Override
    public void updateActiveChatRoom(Long userId, Long openChatRoomId, String activeFlag){
        baseRedisTemplate.putHash(
            RedisKeyGeneration.getChatRoomConnectionInfoKey(userId, openChatRoomId),
            ACTIVE.getField(), activeFlag);
    }
    @Override
    public void incrementUnreadMessageCount(Long userId, Long openChatRoomId){
        baseRedisTemplate.putHash(
            RedisKeyGeneration.getChatRoomConnectionInfoKey(userId, openChatRoomId),
            UN_READ_MESSAGE_COUNT.getField(), 1);
    }
    @Override
    public void updateLastExitTime(Long userId, Long openChatRoomId, String lastExitTime){
        baseRedisTemplate.putHash(
            RedisKeyGeneration.getChatRoomConnectionInfoKey(userId, openChatRoomId),
            LAST_EXIT_TIME.getField(), lastExitTime);
    }
}
