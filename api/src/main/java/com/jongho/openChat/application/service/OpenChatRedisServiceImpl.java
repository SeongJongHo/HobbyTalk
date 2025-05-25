package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.application.repository.OpenChatRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenChatRedisServiceImpl {
    private final OpenChatRedisRepository openChatRedisRepository;

    public Optional<OpenChat> getLastOpenChatByOpenChatRoomId(Long openChatRoomId){
        return openChatRedisRepository.selectLastOpenChatByChatRoomId(openChatRoomId);
    };
    public List<OpenChat> getOpenChatListByOpenChatRoomId(Long openChatRoomId){
        return openChatRedisRepository.selectOpenChatListByChatRoomId(openChatRoomId);
    };
    public List<OpenChat> getOpenChatListByOpenChatRoomIdAndOffsetAndLimit(Long openChatRoomId, int offset, int limit){
        return openChatRedisRepository.selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit(openChatRoomId, offset, limit);
    };
    public void createOpenChat(OpenChat openChat){
        openChatRedisRepository.insertOpenChat(openChat);
    };
    public void updateLastOpenChat(OpenChat openChat){
        openChatRedisRepository.updateLastOpenChat(openChat);
    };
}
