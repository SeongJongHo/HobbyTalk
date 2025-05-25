package com.jongho.openChat.dao.repository;

import com.jongho.openChat.dao.mapper.OpenChatRoomUserMapper;
import com.jongho.openChat.domain.model.OpenChatRoomUser;
import com.jongho.openChat.application.repository.IOpenChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OpenChatRoomUserMybatisRepository implements IOpenChatRoomUserRepository {
    private final OpenChatRoomUserMapper openChatRoomUserMapper;
    @Override
    public void createOpenChatRoomUser(OpenChatRoomUser openChatRoomUser) {
        openChatRoomUserMapper.createOpenChatRoomUser(openChatRoomUser);
    }

    @Override
    public Optional<OpenChatRoomUser> selectOneOpenChatRoomUserByOpenChatRoomIdAndUserId(Long openChatRoomId, Long userId) {
        return Optional.ofNullable(openChatRoomUserMapper.selectOneOpenChatRoomUserByOpenChatRoomIdAndUserId(openChatRoomId, userId));
    }
}
