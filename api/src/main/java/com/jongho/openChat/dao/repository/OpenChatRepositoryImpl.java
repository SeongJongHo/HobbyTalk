package com.jongho.openChat.dao.repository;

import com.jongho.openChat.application.dto.response.OpenChatDto;
import com.jongho.openChat.dao.mapper.OpenChatMapper;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.application.repository.OpenChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OpenChatRepositoryImpl implements OpenChatRepository {
    private final OpenChatMapper openChatMapper;

    @Override
    public Optional<OpenChat> selectLastOpenChatByChatRoomId(Long openChatRoomId) {
        return Optional.ofNullable(openChatMapper.selectLastOpenChatByChatRoomId(openChatRoomId));
    }
    @Override
    public int selectUnReadOpenChatCountByChatRoomIdAndLastExitTime(Long openChatRoomId, String lastExitTime, int limit) {
        return openChatMapper.selectUnReadOpenChatCountByChatRoomIdAndLastExitTime(openChatRoomId, lastExitTime, limit);
    }
    @Override
    public List<OpenChatDto> selectOpenChatByChatRoomIdAndLastCreatedTime(Long openChatRoomId, String lastCreatedTime, int limit) {
        return openChatMapper.selectOpenChatByChatRoomIdAndLastCreatedTime(openChatRoomId, lastCreatedTime, limit);
    }

}
