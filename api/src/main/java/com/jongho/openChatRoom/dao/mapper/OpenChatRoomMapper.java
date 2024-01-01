package com.jongho.openChatRoom.dao.mapper;

import com.jongho.openChatRoom.domain.model.OpenChatRoom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpenChatRoomMapper {
    public int countByManagerId(Long managerId);
    public void createOpenChatRoom(OpenChatRoom openChatRoom);
    public void updateIncrementCurrentCapacity(Long openChatRoomId);
}
