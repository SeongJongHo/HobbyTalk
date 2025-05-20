package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;

import java.util.List;
import java.util.Optional;

public interface OpenChatRoomService {
    void createOpenChatRoom(OpenChatRoom openChatRoom);
    int getOpenChatRoomCountByManagerId(Long managerId);
    Optional<OpenChatRoom> getOpenChatRoomByManagerIdAndTitle(Long managerId, String title);
    void incrementOpenChatRoomCurrentAttendance(Long openChatRoomId, int currentAttendance);
    Optional<OpenChatRoom> getOpenChatRoomByIdForUpdate(Long openChatRoomId);
    Optional<OpenChatRoom> getOpenChatRoomById(Long openChatRoomId);
    void updateOpenChatRoomNotice(Long userId,Long openChatRoomId, String notice);
    List<CachedOpenChatRoom> getJoinOpenChatRoomList(Long userId);
    List<Long> getOpenChatRoomUserList(Long OpenChatRoomId);
    Optional<CachedOpenChatRoom> getRedisOpenChatRoomById(Long openChatRoomId);

}
