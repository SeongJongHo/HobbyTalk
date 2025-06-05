package com.jongho.openChat.application.service;

import com.jongho.openChat.application.dto.response.ReadOpenChatRoomDto;
import com.jongho.openChat.application.repository.IReadOpenChatRoomRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadOpenChatRoomService {

    private final IReadOpenChatRoomRepository readOpenChatRoomRepository;
    private final int PAGE_SIZE = 50;

    @Transactional(readOnly = true)
    public List<ReadOpenChatRoomDto> getOpenChatRooms(Long categoryId,
        LocalDateTime lastCreatedTime, int offset) {
        return readOpenChatRoomRepository.findAllByCategoryId(categoryId, lastCreatedTime, offset,
            PAGE_SIZE).stream().map(ReadOpenChatRoomDto::of).toList();
    }
}
