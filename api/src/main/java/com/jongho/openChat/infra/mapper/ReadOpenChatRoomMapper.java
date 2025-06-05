package com.jongho.openChat.infra.mapper;

import com.jongho.openChat.domain.model.OpenChatRoom;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReadOpenChatRoomMapper {

    @Select("""
        SELECT
            id,
            title,
            notice,
            manager_id,
            category_id,
            maximum_capacity,
            current_attendance,
            password
        FROM open_chat_rooms
        WHERE category_id = #{categoryId} AND created_time < #{createdTime}
        ORDER BY created_time DESC
        LIMIT 0, #{limit}
        """)
    List<OpenChatRoom> selectOpenChatRoomsByCategoryId(
        @Param("categoryId") Long categoryId,
        @Param("createdTime") LocalDateTime createdTime,
        @Param("limit") int limit);

    @Select("""
        SELECT
            id,
            title,
            notice,
            manager_id,
            category_id,
            maximum_capacity,
            current_attendance,
            password
        FROM open_chat_rooms
        WHERE created_time < #{createdTime}
        ORDER BY created_time DESC
        LIMIT 0, #{limit}""")
    List<OpenChatRoom> selectOpenChatRooms(
        @Param("createdTime") LocalDateTime createdTime,
        @Param("limit") int limit);
}
