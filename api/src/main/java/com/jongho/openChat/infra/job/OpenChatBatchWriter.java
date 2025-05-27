package com.jongho.openChat.infra.job;

import com.jongho.common.event.EventType;
import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenChatBatchWriter implements ItemWriter<List<String>>, StepExecutionListener {

    private final BaseRedisTemplate baseRedisTemplate;
    @Override
    public void write(@NotNull Chunk<? extends List<String>> chunk) {
        chunk.forEach(chatRoomIds -> {
            baseRedisTemplate.pipeline(connection -> {
                chatRoomIds.forEach(chatRoomId -> rename(connection, chatRoomId));

                return null;
            });
        });
    }

    private void rename(RedisConnection connection, String chatRoomId) {
        byte[] chatBatchKey = RedisKeyGeneration.getChatBatchKey(Long.parseLong(chatRoomId))
            .getBytes(StandardCharsets.UTF_8);
        byte[] chatBatchProcessingKey = RedisKeyGeneration.getChatBatchProcessingKey(
                Long.parseLong(chatRoomId))
            .getBytes(StandardCharsets.UTF_8);
        if (Boolean.TRUE.equals(connection.keyCommands().exists(chatBatchKey))) {
            connection.keyCommands().rename(chatBatchKey, chatBatchProcessingKey);
        }
    }

    @Override
    public ExitStatus afterStep(@NotNull StepExecution stepExecution) {
        baseRedisTemplate.streamPublish(
            EventType.CHAT_BATCH.getTopic(),
            "PRESENT"
        );
        return ExitStatus.COMPLETED;
    }
}
