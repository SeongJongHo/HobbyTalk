package com.jongho.openChatRoomUser.dao.repository;

import com.jongho.openChatRoomUser.dao.mapper.OpenChatRoomUserMapper;
import com.jongho.openChatRoomUser.domain.model.OpenChatRoomUser;
import com.jongho.openChatRoomUser.application.repository.OpenChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OpenChatRoomUserMybatisRepositoryImpl implements OpenChatRoomUserRepository {
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
