package com.jongho.openChat.application.repository;

import com.jongho.openChat.domain.model.OpenChatRoom;
import java.time.LocalDateTime;
import java.util.List;

public interface IReadOpenChatRoomRepository {

    List<OpenChatRoom> findAllByCategoryId(Long categoryId, LocalDateTime createdTime, int offset,
        int limit);
}
