package com.jongho.openChat.application.repository;

import java.util.List;

public interface IOpenChatBatchGroupRepository {
    void save(Long chatId);
    void rename();
    boolean hasKey();
    List<Long> rPopProcessing(int count);
}
