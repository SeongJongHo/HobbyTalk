package com.jongho.openChat.presentation.controller;

import com.jongho.common.annotaition.HttpRequestLogging;
import com.jongho.common.response.BaseMessageEnum;
import com.jongho.common.response.BaseResponseEntity;
import com.jongho.openChat.application.dto.response.ReadOpenChatRoomDto;
import com.jongho.openChat.application.service.ReadOpenChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "OpenChatRoom", description = "오픈채팅방 API")
@RestController
@RequestMapping("/api/v1/public/open-chat-rooms")
@RequiredArgsConstructor
public class ReadOpenChatRoomController {

    private final ReadOpenChatRoomService readOpenChatRoomService;

    @Operation(summary = "오픈채팅방 불러오기")
    @GetMapping
    public ResponseEntity<BaseResponseEntity<List<ReadOpenChatRoomDto>>> getOpenChatRooms(
        @RequestParam(required = false, defaultValue = "0") Long categoryId,
        @RequestParam(required = false) String lastCreatedTime,
        @RequestParam(defaultValue = "0") int offset) {

        List<ReadOpenChatRoomDto> dto = readOpenChatRoomService.getOpenChatRooms(
            categoryId,
            lastCreatedTime != null ? LocalDateTime.parse(lastCreatedTime) : LocalDateTime.now(),
            offset
        );
        return BaseResponseEntity.ok(dto, BaseMessageEnum.SUCCESS.getValue());
    }
}
