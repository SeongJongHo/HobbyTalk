package com.jongho.openChatRoom.application.service;

import com.jongho.openChatRoom.application.dto.response.OpenChatRoomDto;
import com.jongho.openChatRoom.domain.model.OpenChatRoom;

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
    public List<OpenChatRoomDto> getJoinOpenChatRoomList(Long userId);
}
