package com.jongho.openChat.infra.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenChatRoomIdsItemReader implements ItemReader<String> {
    private final StringRedisTemplate stringRedisTemplate;
    private final static String PATTERN = "chatRooms:*:chats";
    private final static int BATCH_SIZE = 2000;
    private Cursor<String> cursor;

    public void reset() {
        if (this.cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        ScanOptions options = ScanOptions.scanOptions()
            .match(PATTERN)
            .count(BATCH_SIZE)
            .build();
        this.cursor = stringRedisTemplate.scan(options);
    }

    @Override
    public String read() {
        String result = null;
        if (cursor != null && cursor.hasNext()) {
            result = cursor.next();
        }

        return result;
    }
}
