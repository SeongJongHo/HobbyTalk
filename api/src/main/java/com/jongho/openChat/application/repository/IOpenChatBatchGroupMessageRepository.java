package com.jongho.openChat.application.repository;

import java.util.List;

public interface IOpenChatBatchGroupMessageRepository {
    void save(Long chatId);
    void rename();
    boolean hasKey();
    List<Long> rPopProcessing(int count);
}
