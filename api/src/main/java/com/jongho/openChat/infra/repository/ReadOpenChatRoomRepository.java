package com.jongho.openChat.infra.repository;

import com.jongho.common.cache.CustomCacheType;
import com.jongho.common.cache.CustomCacheable;
import com.jongho.openChat.application.repository.IReadOpenChatRoomRepository;
import com.jongho.openChat.common.enums.CacheSize;
import com.jongho.openChat.domain.model.OpenChatRoom;
import com.jongho.openChat.infra.mapper.ReadOpenChatRoomMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReadOpenChatRoomRepository implements IReadOpenChatRoomRepository {

    private final ReadOpenChatRoomMapper readOpenChatRoomMapper;

    @CustomCacheable(
        prefix = "chatRooms",
        keys = {"categoryId"},
        type = CustomCacheType.Type.LIST,
        ttlSeconds = 60,
        cacheSize = CacheSize.Size.CHAT_ROOM
    )
    @Override
    public List<OpenChatRoom> findAllByCategoryId(
        Long categoryId,
        LocalDateTime createdTime,
        int offset,
        int limit
    ) {
        if (categoryId == null || categoryId == 0L) {
            return readOpenChatRoomMapper.selectOpenChatRooms(createdTime, limit);
        }
        return readOpenChatRoomMapper.selectOpenChatRoomsByCategoryId(categoryId, createdTime,
            limit);
    }
}
