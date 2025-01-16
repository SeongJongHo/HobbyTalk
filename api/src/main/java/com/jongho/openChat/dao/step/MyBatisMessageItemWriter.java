package com.jongho.openChat.dao.step;

import com.jongho.openChat.dao.mapper.OpenChatMapper;
import com.jongho.openChat.domain.model.OpenChat;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Redis에 저장되어 있던 채팅 메시지를 가져와서 MySQL에 벌크인서트 하는 객체
 */
@Component
@RequiredArgsConstructor
public class MyBatisMessageItemWriter implements ItemWriter<List<OpenChat>> {

    private final OpenChatMapper openChatMapper;

    @Override
    public void write(Chunk<? extends List<OpenChat>> chunk) {
        for (List<OpenChat> batch : chunk) {
            openChatMapper.insertBatch(batch);
        }
    }
}
