package com.jongho.openChat.infra.step;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.openChat.domain.model.OpenChat;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis에 저장된 채팅 메시지를 읽어오는 ItemReader
 * 채팅 메시지를 일정 배치 단위로 가져오기 위해 Cursor를 활용하여,
 * 매번 새로운 키(채팅방) 혹은 현재 키의 남은 메시지를 읽어오는 방식으로 처리
 */
@Component
@RequiredArgsConstructor
public class RedisMessageItemReader implements ItemReader<List<OpenChat>> {

    private final BaseRedisTemplate redisTemplate;
    private Cursor<String> cursor;
    private String currentKey;
    private boolean continueCurrentKey = false;

    /**
     * Redis에서 채팅 메시지를 읽어오는 메서드.
     * 1. 만약 현재 키에 아직 처리하지 않은 메시지가 있다면(`continueCurrentKey == true`),
     *    해당 메시지를 먼저 처리한 뒤 반환
     * 2. 처리할 메시지가 더 이상 없다면, 다음 채팅방 키를 스캔하여 메시지를 가져dha
     *    한 번에 처리 가능한 개수(100개)만큼만 가져오고, 남아 있으면 다음 read()에서 이어서 처리
     * 3. 더 이상 처리할 메시지가 없으면 null을 반환하여 배치 Step 종료
     * @return 메시지를 담은 List<OpenChat> (비어 있지 않다면 해당 배치를 처리), 더 이상 없으면 null
     */
    @Override
    public List<OpenChat> read() {
        List<OpenChat> openChats = new ArrayList<>();

        // 1) 이미 처리가 덜 끝난 currentKey가 있다면, 먼저 처리
        if (continueCurrentKey && currentKey != null) {
            openChats.addAll(processCurrentKey());
            if (!openChats.isEmpty()) {
                return openChats;
            }
            continueCurrentKey = false;
            currentKey = null;
        }

        // 2) 아직 키(Cursor)가 없거나 소진되었다면 새로 키를 스캔
        if (cursor == null || !cursor.hasNext()) {
            String chatRoomPattern = "chatRooms:*:chats";
            ScanOptions options = ScanOptions.scanOptions()
                    .match(chatRoomPattern)
                    .count(100)
                    .build();
            cursor = redisTemplate.scan(options);
        }

        // 3) Cursor로 채팅방 키를 순회하며, 키별 메시지를 읽어온다.
        while (cursor != null && cursor.hasNext()) {
            currentKey = cursor.next();
            openChats.addAll(processCurrentKey());
            if (!openChats.isEmpty()) {
                continueCurrentKey = true;
                return openChats;
            }
            currentKey = null;
        }

        return null;
    }

    /**
     * 현재 키(currentKey)에서 메시지를 가져와서 처리(배치로 반환)하는 메서드
     * @return Redis에서 읽어온 채팅 메시지 목록
     */
    private List<OpenChat> processCurrentKey() {
        List<OpenChat> openChats = new ArrayList<>();
        if (currentKey != null) {
            List<OpenChat> batch = redisTemplate.popListData("chatRooms:" + currentKey + ":chats", OpenChat.class, 100);
            if (batch != null && !batch.isEmpty()) {
                openChats.addAll(batch);
            }
        }
        return openChats;
    }
}
