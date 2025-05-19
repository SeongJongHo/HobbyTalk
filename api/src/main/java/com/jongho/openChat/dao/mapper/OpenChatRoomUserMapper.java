package com.jongho.openChat.dao.mapper;

import com.jongho.openChat.domain.model.OpenChatRoomUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpenChatRoomUserMapper {
    void createOpenChatRoomUser(OpenChatRoomUser openChatRoomUser);
    OpenChatRoomUser selectOneOpenChatRoomUserByOpenChatRoomIdAndUserId(@Param("openChatRoomId") Long openChatRoomId, @Param("userId") Long userId);
}
