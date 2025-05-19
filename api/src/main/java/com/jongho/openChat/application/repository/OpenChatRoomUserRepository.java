package com.jongho.openChat.application.repository;

import com.jongho.openChat.domain.model.OpenChatRoomUser;

import java.util.Optional;

public interface OpenChatRoomUserRepository {
    public void createOpenChatRoomUser(OpenChatRoomUser openChatRoomUser);
    public Optional<OpenChatRoomUser> selectOneOpenChatRoomUserByOpenChatRoomIdAndUserId(Long openChatRoomId, Long userId);
}
