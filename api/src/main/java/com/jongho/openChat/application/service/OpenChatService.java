package com.jongho.openChat.application.service;

import com.jongho.openChat.application.dto.response.OpenChatDto;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.application.repository.IOpenChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenChatService {
    private final IOpenChatRepository IOpenChatRepository;
    public Optional<OpenChat> getLastOpenChatByOpenChatRoomId(Long openChatRoomId){
        return IOpenChatRepository.selectLastOpenChatByChatRoomId(openChatRoomId);
    };
    public int getUnReadOpenChatCountByOpenChatRoomIdAndLastExitTime(Long openChatRoomId, String lastCreatedTime, int limit){
        return IOpenChatRepository.selectUnReadOpenChatCountByChatRoomIdAndLastExitTime(openChatRoomId, lastCreatedTime, limit);
    }
    public List<OpenChatDto> getOpenChatByOpenChatRoomIdAndLastCreatedTime(Long openChatRoomId, String lastCreatedTime, int limit){
        return IOpenChatRepository.selectOpenChatByChatRoomIdAndLastCreatedTime(openChatRoomId, lastCreatedTime, limit);
    }

}
