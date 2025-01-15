package com.jongho.openChatRoomUser.application.service;

import com.jongho.openChatRoomUser.domain.model.OpenChatRoomUser;
import com.jongho.openChatRoomUser.application.repository.OpenChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenChatRoomUserServiceImpl implements OpenChatRoomUserService{
    private final OpenChatRoomUserRepository openChatRoomUserRepository;
    @Override
    public void createOpenChatRoomUser(OpenChatRoomUser openChatRoomUser) {
        openChatRoomUserRepository.createOpenChatRoomUser(openChatRoomUser);
    }
    @Override
    public Optional<OpenChatRoomUser> getOpenChatRoomUserByOpenChatRoomIdAndUserId(Long openChatRoomId, Long userId) {
        return openChatRoomUserRepository.selectOneOpenChatRoomUserByOpenChatRoomIdAndUserId(openChatRoomId, userId);
    }
}
