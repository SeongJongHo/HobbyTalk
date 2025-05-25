package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.application.repository.IOpenChatRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenChatRedisService {
    private final IOpenChatRedisRepository IOpenChatRedisRepository;

    public Optional<OpenChat> getLastOpenChatByOpenChatRoomId(Long openChatRoomId){
        return IOpenChatRedisRepository.selectLastOpenChatByChatRoomId(openChatRoomId);
    };
    public List<OpenChat> getOpenChatListByOpenChatRoomId(Long openChatRoomId){
        return IOpenChatRedisRepository.selectOpenChatListByChatRoomId(openChatRoomId);
    };
    public List<OpenChat> getOpenChatListByOpenChatRoomIdAndOffsetAndLimit(Long openChatRoomId, int offset, int limit){
        return IOpenChatRedisRepository.selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit(openChatRoomId, offset, limit);
    };
    public void createOpenChat(OpenChat openChat){
        IOpenChatRedisRepository.insertOpenChat(openChat);
    };
    public void updateLastOpenChat(OpenChat openChat){
        IOpenChatRedisRepository.updateLastOpenChat(openChat);
    };
}
