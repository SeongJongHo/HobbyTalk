package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChatRoomUser;

import java.util.Optional;

public interface OpenChatRoomUserService {
    public void createOpenChatRoomUser(OpenChatRoomUser openChatRoomUser);
    public Optional<OpenChatRoomUser> getOpenChatRoomUserByOpenChatRoomIdAndUserId(Long openChatRoomId, Long userId);
}
