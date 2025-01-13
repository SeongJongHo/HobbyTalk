package com.jongho.openChatRoomMembershipRequest.dao.mapper;

import com.jongho.openChatRoomMembershipRequest.domain.model.OpenChatRoomMembershipRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OpenChatRoomMembershipRequestMapper {

    public int countByRequesterIdAndStatus(@Param("requesterId") Long requesterId, @Param("status") int status);
    public boolean existsByRequesterIdAndOpenChatRoomIdAndStatus(@Param("requesterId") Long requesterId, @Param("openChatRoomId") Long openChatRoomId, @Param("status") int status);
    public void createOpenChatRoomMembershipRequest(OpenChatRoomMembershipRequest openChatRoomMembershipRequest);
}
