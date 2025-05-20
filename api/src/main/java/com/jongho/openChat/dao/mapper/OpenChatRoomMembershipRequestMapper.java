package com.jongho.openChat.dao.mapper;

import com.jongho.openChat.domain.model.OpenChatRoomMembershipRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpenChatRoomMembershipRequestMapper {

    int countByRequesterIdAndStatus(@Param("requesterId") Long requesterId, @Param("status") int status);
    boolean existsByRequesterIdAndOpenChatRoomIdAndStatus(@Param("requesterId") Long requesterId, @Param("openChatRoomId") Long openChatRoomId, @Param("status") int status);
    void createOpenChatRoomMembershipRequest(OpenChatRoomMembershipRequest openChatRoomMembershipRequest);
}
