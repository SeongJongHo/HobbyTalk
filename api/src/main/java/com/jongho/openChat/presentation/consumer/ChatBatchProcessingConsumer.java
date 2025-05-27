package com.jongho.openChat.presentation.consumer;

import com.jongho.openChat.application.service.OpenChatBatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component("chatBatchProcessingConsumer")
@RequiredArgsConstructor
public class ChatBatchProcessingConsumer implements
    StreamListener<String, MapRecord<String, String, String>> {

    private final OpenChatBatchService openChatBatchService;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        try {
            openChatBatchService.process();
        } catch (Exception e) {
            log.error("Error occurred during OpenChat Batch execution", e);
        }
    }
}
