package com.jongho.openChat.application.repository;

import com.jongho.openChat.domain.model.OpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;

import java.util.List;
import java.util.Optional;

public interface IOpenChatRoomRepository {
    int countByManagerId(Long managerId);
    void createOpenChatRoom(OpenChatRoom openChatRoom);
    void updateIncrementCurrentCapacity(Long openChatRoomId, int currentAttendance);
    Optional<OpenChatRoom> selectOneOpenChatRoomByManagerIdAndTitle(Long managerId, String title);
    Optional<OpenChatRoom> selectOneOpenChatRoomByIdForUpdate(Long openChatRoomId);
    Optional<OpenChatRoom> selectOneOpenChatRoomById(Long openChatRoomId);
    void updateOpenChatRoomNotice(Long openChatRoomId, String notice);
    List<CachedOpenChatRoom> selectJoinOpenChatRoomByUserId(Long userId);
    List<Long> selectOpenChatRoomUser(Long openChatRoomId);
    Optional<CachedOpenChatRoom> selectRedisOpenChatRoomById(Long openChatRoomId);
}
