package com.jongho.openChat.application.service;

import com.jongho.openChat.domain.model.OpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;

import java.util.List;
import java.util.Optional;

public interface OpenChatRoomService {
    public void createOpenChatRoom(OpenChatRoom openChatRoom);
    public int getOpenChatRoomCountByManagerId(Long managerId);
    public Optional<OpenChatRoom> getOpenChatRoomByManagerIdAndTitle(Long managerId, String title);
    public void incrementOpenChatRoomCurrentAttendance(Long openChatRoomId, int currentAttendance);
    public Optional<OpenChatRoom> getOpenChatRoomByIdForUpdate(Long openChatRoomId);
    public Optional<OpenChatRoom> getOpenChatRoomById(Long openChatRoomId);
    public void updateOpenChatRoomNotice(Long userId,Long openChatRoomId, String notice);
    public List<CachedOpenChatRoom> getJoinOpenChatRoomList(Long userId);
    public List<Long> getOpenChatRoomUserList(Long OpenChatRoomId);
    public Optional<CachedOpenChatRoom> getRedisOpenChatRoomById(Long openChatRoomId);

}
