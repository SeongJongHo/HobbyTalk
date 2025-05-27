package com.jongho.openChat.infra.job;

import com.jongho.common.util.redis.BaseRedisTemplate;
import com.jongho.common.util.redis.RedisKeyGeneration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenChatBatchReader implements ItemReader<List<String>>, StepExecutionListener {

    private final BaseRedisTemplate stringRedisTemplate;
    private final static int BATCH_SIZE = 10000;

    @Override
    public List<String> read() {
         List<String> ids = stringRedisTemplate.rPopList(RedisKeyGeneration.getChatGroupProcessingKey(), String.class,
            BATCH_SIZE);
         if (ids == null || ids.isEmpty()) {
             return null;
         }

         return ids;
    }

}
