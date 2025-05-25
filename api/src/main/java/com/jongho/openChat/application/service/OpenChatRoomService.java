package com.jongho.openChat.application.service;

import com.jongho.common.exception.OpenChatRoomNotFoundException;
import com.jongho.common.exception.UnAuthorizedException;
import com.jongho.openChat.domain.model.OpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChat.application.repository.IOpenChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenChatRoomService {
    private final IOpenChatRoomRepository IOpenChatRoomRepository;
    public void createOpenChatRoom(OpenChatRoom openChatRoom) {
        IOpenChatRoomRepository.createOpenChatRoom(openChatRoom);
    }
    public int getOpenChatRoomCountByManagerId(Long managerId) {
        return IOpenChatRoomRepository.countByManagerId(managerId);
    }
    public void incrementOpenChatRoomCurrentAttendance(Long openChatRoomId, int currentAttendance) {
        IOpenChatRoomRepository.updateIncrementCurrentCapacity(openChatRoomId, currentAttendance);
    }
    public Optional<OpenChatRoom> getOpenChatRoomByManagerIdAndTitle(Long managerId, String title) {
        return IOpenChatRoomRepository.selectOneOpenChatRoomByManagerIdAndTitle(managerId, title);
    }
    public Optional<OpenChatRoom> getOpenChatRoomByIdForUpdate(Long openChatRoomId) {
        return IOpenChatRoomRepository.selectOneOpenChatRoomByIdForUpdate(openChatRoomId);
    }
    public Optional<OpenChatRoom> getOpenChatRoomById(Long openChatRoomId) {
        return IOpenChatRoomRepository.selectOneOpenChatRoomById(openChatRoomId);
    }
    public void updateOpenChatRoomNotice(Long userId, Long openChatRoomId, String notice) {
        OpenChatRoom openChatRoom= this.getOpenChatRoomById(openChatRoomId).orElseThrow(() -> new OpenChatRoomNotFoundException("존재하지 않는 채팅방입니다."));
        if(!openChatRoom.getManagerId().equals(userId)){
            throw new UnAuthorizedException("채팅방 공지 수정 권한이 없습니다.");
        }
        IOpenChatRoomRepository.updateOpenChatRoomNotice(openChatRoomId, notice);
    }
    public List<CachedOpenChatRoom> getJoinOpenChatRoomList(Long userId) {
        return IOpenChatRoomRepository.selectJoinOpenChatRoomByUserId(userId);
    }
    public List<Long> getOpenChatRoomUserList(Long openChatRoomId) {
        return IOpenChatRoomRepository.selectOpenChatRoomUser(openChatRoomId);
    }
    public Optional<CachedOpenChatRoom> getRedisOpenChatRoomById(Long openChatRoomId) {
        return IOpenChatRoomRepository.selectRedisOpenChatRoomById(openChatRoomId);
    }
}
