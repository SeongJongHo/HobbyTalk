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
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenChatBatchWriter implements ItemWriter<List<String>>, StepExecutionListener {

    private final BaseRedisTemplate baseRedisTemplate;
    private static int COUNT = 0;
    @Override
    public void write(@NotNull Chunk<? extends List<String>> chunk) {
        chunk.forEach(chatRoomIds -> {
            baseRedisTemplate.pipeline(connection -> {
                chatRoomIds.forEach(chatRoomId -> {
                    connection.keyCommands().rename(
                        RedisKeyGeneration.getChatBatchKey(Long.parseLong(chatRoomId))
                            .getBytes(StandardCharsets.UTF_8),
                        RedisKeyGeneration.getChatBatchProcessingKey(Long.parseLong(chatRoomId))
                            .getBytes(StandardCharsets.UTF_8));
                });
                return null;
            });
        });
        COUNT += 10000;
        log.info("OpenChatBatchWriter write count: {}", COUNT);
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
