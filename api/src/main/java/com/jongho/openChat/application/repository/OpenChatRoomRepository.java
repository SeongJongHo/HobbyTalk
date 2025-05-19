package com.jongho.openChat.application.repository;

import com.jongho.openChat.domain.model.OpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;

import java.util.List;
import java.util.Optional;

public interface OpenChatRoomRepository {
    public int countByManagerId(Long managerId);
    public void createOpenChatRoom(OpenChatRoom openChatRoom);
    public void updateIncrementCurrentCapacity(Long openChatRoomId, int currentAttendance);
    public Optional<OpenChatRoom> selectOneOpenChatRoomByManagerIdAndTitle(Long managerId, String title);
    public Optional<OpenChatRoom> selectOneOpenChatRoomByIdForUpdate(Long openChatRoomId);
    public Optional<OpenChatRoom> selectOneOpenChatRoomById(Long openChatRoomId);
    public void updateOpenChatRoomNotice(Long openChatRoomId, String notice);
    public List<CachedOpenChatRoom> selectJoinOpenChatRoomByUserId(Long userId);
    public List<Long> selectOpenChatRoomUser(Long openChatRoomId);
    public Optional<CachedOpenChatRoom> selectRedisOpenChatRoomById(Long openChatRoomId);
}
