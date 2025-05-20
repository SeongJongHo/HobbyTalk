package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChatRoomUser;

import java.util.Optional;

public interface OpenChatRoomUserService {
    void createOpenChatRoomUser(OpenChatRoomUser openChatRoomUser);
    Optional<OpenChatRoomUser> getOpenChatRoomUserByOpenChatRoomIdAndUserId(Long openChatRoomId, Long userId);
}
