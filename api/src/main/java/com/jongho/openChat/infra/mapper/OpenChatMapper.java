package com.jongho.openChat.infra.mapper;

import com.jongho.openChat.application.dto.response.OpenChatDto;
import com.jongho.openChat.domain.model.OpenChat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpenChatMapper {
    OpenChat selectLastOpenChatByChatRoomId(Long openChatRoomId);
    int selectUnReadOpenChatCountByChatRoomIdAndLastExitTime(
            @Param("openChatRoomId") Long openChatRoomId,
            @Param("lastExitTime") String lastExitTime,
            @Param("limit") int limit);
    List<OpenChatDto> selectOpenChatByChatRoomIdAndLastCreatedTime(
            @Param("openChatRoomId") Long openChatRoomId,
            @Param("lastCreatedTime") String lastCreatedTime,
            @Param("limit") int limit);
    void insertBatch(@Param("openChats") List<OpenChat> openChats);
}
