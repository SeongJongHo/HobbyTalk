package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.application.repository.OpenChatRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenChatRedisServiceImpl implements OpenChatRedisService {
    private final OpenChatRedisRepository openChatRedisRepository;

    @Override
    public Optional<OpenChat> getLastOpenChatByOpenChatRoomId(Long openChatRoomId){
        return openChatRedisRepository.selectLastOpenChatByChatRoomId(openChatRoomId);
    };
    @Override
    public List<OpenChat> getOpenChatListByOpenChatRoomId(Long openChatRoomId){
        return openChatRedisRepository.selectOpenChatListByChatRoomId(openChatRoomId);
    };
    @Override
    public List<OpenChat> getOpenChatListByOpenChatRoomIdAndOffsetAndLimit(Long openChatRoomId, int offset, int limit){
        return openChatRedisRepository.selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit(openChatRoomId, offset, limit);
    };
    @Override
    public void createOpenChat(OpenChat openChat){
        openChatRedisRepository.insertOpenChat(openChat);
    };
    @Override
    public void updateLastOpenChat(OpenChat openChat){
        openChatRedisRepository.updateLastOpenChat(openChat);
    };
}
