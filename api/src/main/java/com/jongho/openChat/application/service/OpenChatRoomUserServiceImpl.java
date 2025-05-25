package com.jongho.openChat.application.service;

import com.jongho.openChat.application.repository.OpenChatRoomUserRepository;
import com.jongho.openChat.domain.model.OpenChatRoomUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenChatRoomUserServiceImpl {

    private final OpenChatRoomUserRepository openChatRoomUserRepository;

    public void createOpenChatRoomUser(OpenChatRoomUser openChatRoomUser) {
        openChatRoomUserRepository.createOpenChatRoomUser(openChatRoomUser);
    }

    public Optional<OpenChatRoomUser> getOpenChatRoomUserByOpenChatRoomIdAndUserId(
        Long openChatRoomId, Long userId) {
        return openChatRoomUserRepository.selectOneOpenChatRoomUserByOpenChatRoomIdAndUserId(
            openChatRoomId, userId);
    }
}
