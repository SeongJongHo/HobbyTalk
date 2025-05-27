package com.jongho.openChat.infra.job;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import com.jongho.openChat.domain.model.OpenChat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenChatBatchProcessingReader implements ItemReader<List<OpenChat>>,
    StepExecutionListener {

    private final BaseRedisTemplate stringRedisTemplate;
    private final static int BATCH_SIZE = 10000;

    @Override
    public List<OpenChat> read() {
        List<String> ids = stringRedisTemplate.rPopList(
                RedisKeyGeneration.getChatGroupMessageProcessingKey(), String.class,
                BATCH_SIZE)
            .stream()
            .map(id -> RedisKeyGeneration.getChatMessageKey(Long.parseLong(id)))
            .toList();
        if (ids.isEmpty()) {
            return null;
        }

        List<OpenChat> openChats = stringRedisTemplate.mGet(ids, OpenChat.class);
        if (openChats == null || openChats.isEmpty()) {
            return null;
        }
        return openChats;
    }

}