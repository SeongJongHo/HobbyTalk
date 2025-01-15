package com.jongho.openChatRoomUser.application.repository;

import com.jongho.openChatRoomUser.domain.model.OpenChatRoomUser;

import java.util.Optional;

public interface OpenChatRoomUserRepository {
    public void createOpenChatRoomUser(OpenChatRoomUser openChatRoomUser);
    public Optional<OpenChatRoomUser> selectOneOpenChatRoomUserByOpenChatRoomIdAndUserId(Long openChatRoomId, Long userId);
}
