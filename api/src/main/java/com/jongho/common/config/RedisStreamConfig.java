package com.jongho.common.config;

import com.jongho.common.event.EventType;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;
import org.springframework.data.redis.stream.Subscription;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisStreamConfig {

    private static final String CHAT_BATCH_STREAM = EventType.CHAT_BATCH.getTopic();
    private static final String CHAT_GROUP = "chatBatchGroup";
    private static final String CHAT_BATCH_CONSUMER = "instance-1" + UUID.randomUUID();
    private final StringRedisTemplate redisTemplate;

    @PostConstruct
    public void ensureConsumerGroup() {
        try {
            redisTemplate.opsForStream()
                .createGroup(CHAT_BATCH_STREAM, ReadOffset.from("0-0"), CHAT_GROUP);
        } catch (Exception e) {

        }
    }

    @Bean
    public StreamMessageListenerContainer<String, MapRecord<String, String, String>>
    streamListenerContainer(RedisConnectionFactory factory) {
        StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
            StreamMessageListenerContainerOptions.builder()
                .batchSize(1)
                .pollTimeout(Duration.ofSeconds(2))
                .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container =
            StreamMessageListenerContainer.create(factory, options);
        container.start();

        return container;
    }

    @Bean
    public Subscription chatBatchSubscription(
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container,
        @Qualifier("chatBatchProcessingConsumer")
        StreamListener<String, MapRecord<String, String, String>> listener) {

        Consumer consumer = Consumer.from(CHAT_GROUP, CHAT_BATCH_CONSUMER);
        StreamOffset<String> offset =
            StreamOffset.create(CHAT_BATCH_STREAM, ReadOffset.lastConsumed());

        return container.receive(consumer, offset, listener);
    }
}
