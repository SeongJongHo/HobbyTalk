package com.jongho.openChat.infra.repository;

import com.jongho.openChat.infra.mapper.OpenChatRoomMembershipRequestMapper;
import com.jongho.openChat.domain.model.OpenChatRoomMembershipRequest;
import com.jongho.openChat.application.repository.IOpenChatRoomMembershipRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OpenChatRoomMembershipRequestRepository implements
    IOpenChatRoomMembershipRequestRepository {
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
