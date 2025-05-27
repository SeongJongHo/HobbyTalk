package com.jongho.openChat.infra.job;

import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.infra.repository.OpenChatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenChatBatchProcessingWriter implements ItemWriter<List<OpenChat>> {

    private final OpenChatRepository openChatRepository;

    @Override
    public void write(@NotNull Chunk<? extends List<OpenChat>> chunk) {
        chunk.forEach(openChatRepository::insertBatch);
    }
}