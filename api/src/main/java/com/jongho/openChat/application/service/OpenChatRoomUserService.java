package com.jongho.openChat.application.service;

import com.jongho.openChat.application.repository.IOpenChatRoomUserRepository;
import com.jongho.openChat.domain.model.OpenChatRoomUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenChatRoomUserService {

    private final IOpenChatRoomUserRepository IOpenChatRoomUserRepository;

    public void createOpenChatRoomUser(OpenChatRoomUser openChatRoomUser) {
        IOpenChatRoomUserRepository.createOpenChatRoomUser(openChatRoomUser);
    }

    public Optional<OpenChatRoomUser> getOpenChatRoomUserByOpenChatRoomIdAndUserId(
        Long openChatRoomId, Long userId) {
        return IOpenChatRoomUserRepository.selectOneOpenChatRoomUserByOpenChatRoomIdAndUserId(
            openChatRoomId, userId);
    }
}
