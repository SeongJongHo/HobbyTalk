package com.jongho.common.event;

import com.jongho.common.event.data.ChatBatchData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    EMAIL_VERIFY(Topic.CHAT_BATCH, ChatBatchData.class);

    private final String topic;
    private final Class<? extends EventData> dataClass;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("EventType type={}", type, e);
            return null;
        }
    }

    public static class Topic {

        public static final String CHAT_BATCH = "chat-batch";
    }
}
