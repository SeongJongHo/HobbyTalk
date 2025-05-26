package com.jongho.openChat.application.repository;

public interface IOpenChatBatchGroupRepository {
    void save(Long chatId);
    void rename();
}
