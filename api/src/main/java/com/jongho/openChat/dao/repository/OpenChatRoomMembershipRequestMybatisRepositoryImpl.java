package com.jongho.openChat.dao.repository;

import com.jongho.openChat.dao.mapper.OpenChatRoomMembershipRequestMapper;
import com.jongho.openChat.domain.model.OpenChatRoomMembershipRequest;
import com.jongho.openChat.application.repository.OpenChatRoomMembershipRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OpenChatRoomMembershipRequestMybatisRepositoryImpl implements OpenChatRoomMembershipRequestRepository {
    private final OpenChatRoomMembershipRequestMapper openChatRoomMapper;
    @Override
    public int countByRequesterIdAndStatus(Long requesterId, int status) {
        return openChatRoomMapper.countByRequesterIdAndStatus(requesterId, status);
    }
    @Override
    public boolean existsByRequesterIdAndOpenChatRoomIdAndStatus(Long requesterId, Long openChatRoomId, int status) {
        return openChatRoomMapper.existsByRequesterIdAndOpenChatRoomIdAndStatus(requesterId, openChatRoomId, status);
    }
    @Override
    public void createOpenChatRoomMembershipRequest(OpenChatRoomMembershipRequest openChatRoomMembershipRequest) {
        openChatRoomMapper.createOpenChatRoomMembershipRequest(openChatRoomMembershipRequest);
    }
}
