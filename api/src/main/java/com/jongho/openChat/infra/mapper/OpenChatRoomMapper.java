package com.jongho.openChat.infra.mapper;

import com.jongho.openChat.domain.model.OpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpenChatRoomMapper {
    int countByManagerId(Long managerId);
    void createOpenChatRoom(OpenChatRoom openChatRoom);
    void updateIncrementCurrentCapacity(@Param("id") Long id, @Param("currentAttendance") int currentAttendance);
    OpenChatRoom selectOneOpenChatRoomByIdForUpdate(@Param("id") Long id);
    OpenChatRoom selectOneOpenChatRoomById(@Param("id") Long id);
    void updateOpenChatRoomNotice(@Param("id") Long id, @Param("notice") String notice);
    OpenChatRoom selectOneOpenChatRoomByManagerIdAndTitle(@Param("managerId") Long managerId, @Param("title") String title);
    List<CachedOpenChatRoom> selectJoinOpenChatRoomByUserId(Long userId);
    List<Long> selectOpenChatRoomUser(Long openChatRoomId);
    CachedOpenChatRoom selectRedisOpenChatRoomById(Long openChatRoomId);
}
