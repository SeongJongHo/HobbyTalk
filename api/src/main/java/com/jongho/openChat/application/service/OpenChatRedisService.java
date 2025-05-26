package com.jongho.openChat.application.service;

import com.jongho.openChat.application.repository.IOpenChatBatchRepository;
import com.jongho.openChat.application.repository.IOpenChatBatchGroupRepository;
import com.jongho.openChat.common.enums.CacheSize;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.application.repository.IOpenChatRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenChatRedisService {
    private final IOpenChatRedisRepository iOpenChatRedisRepository;
    private final IOpenChatBatchGroupRepository iOpenChatBatchGroupRepository;
    private final IOpenChatBatchRepository iOpenChatBatchRepository;

    public Optional<OpenChat> getLastOpenChatByOpenChatRoomId(Long openChatRoomId){
        return iOpenChatRedisRepository.selectLastOpenChatByChatRoomId(openChatRoomId);
    }

    public List<OpenChat> getOpenChatListByOpenChatRoomId(Long openChatRoomId){
        return iOpenChatRedisRepository.selectOpenChatListByChatRoomId(openChatRoomId);
    }

    public List<OpenChat> getOpenChatListByOpenChatRoomIdAndOffsetAndLimit(Long openChatRoomId, int offset, int limit){
        return iOpenChatRedisRepository.selectOpenChatListByOpenChatRoomIdAndOffsetAndLimit(openChatRoomId, offset, limit);
    }

    public void createOpenChat(OpenChat openChat){
        iOpenChatRedisRepository.save(openChat);
        if(iOpenChatRedisRepository.getSize(openChat.getOpenChatRoomId()) > CacheSize.CHAT.getSize()) {
            iOpenChatRedisRepository.trimCacheToSize(openChat.getOpenChatRoomId());
        }
        iOpenChatBatchGroupRepository.save(openChat.getSnowflakeId());
        iOpenChatBatchRepository.save(openChat.getOpenChatRoomId(), openChat.getSnowflakeId());

    }

    public void updateLastOpenChat(OpenChat openChat){
        iOpenChatRedisRepository.updateLastOpenChat(openChat);
    }
}
