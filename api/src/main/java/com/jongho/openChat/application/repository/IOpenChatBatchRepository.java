package com.jongho.openChat.application.repository;

public interface IOpenChatBatchRepository {
    public void save(Long chatRoomId, Long chatId);

    void rename(Long chatRoomId);
}
