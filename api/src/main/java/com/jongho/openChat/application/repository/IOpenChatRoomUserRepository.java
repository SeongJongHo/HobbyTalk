package com.jongho.openChat.application.repository;

import com.jongho.openChat.domain.model.OpenChatRoomUser;

import java.util.Optional;

public interface IOpenChatRoomUserRepository {
    void createOpenChatRoomUser(OpenChatRoomUser openChatRoomUser);
    Optional<OpenChatRoomUser> selectOneOpenChatRoomUserByOpenChatRoomIdAndUserId(Long openChatRoomId, Long userId);
}
