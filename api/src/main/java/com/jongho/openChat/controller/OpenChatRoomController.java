package com.jongho.openChat.controller;

import com.jongho.common.annotaition.HttpRequestLogging;
import com.jongho.common.response.BaseMessageEnum;
import com.jongho.common.response.BaseResponseEntity;
import com.jongho.common.util.threadlocal.AuthenticatedUserThreadLocalManager;
import com.jongho.openChat.application.dto.request.OpenChatRoomCreateDto;
import com.jongho.openChat.application.dto.request.OpenChatRoomJoinDto;
import com.jongho.openChat.application.dto.request.OpenChatRoomMembershipRequestDto;
import com.jongho.openChat.application.dto.request.OpenChatRoomNoticeUpdateDto;
import com.jongho.openChat.application.facade.OpenChatRoomFacade;
import com.jongho.openChat.application.service.OpenChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "OpenChatRoom", description = "오픈채팅방 API")
@RestController
@RequiredArgsConstructor
@HttpRequestLogging
@RequestMapping("/api/v1/open-chat-rooms")
public class OpenChatRoomController {
    private final OpenChatRoomFacade openChatRoomFacade;
    private final OpenChatRoomService openChatRoomService;

    @Operation(summary = "오픈채팅방 생성")
    @PostMapping
    public ResponseEntity<BaseResponseEntity<?>> createOpenChatRoom(@Validated @RequestBody OpenChatRoomCreateDto openChatRoomCreateDto){
        openChatRoomFacade.createOpenChatRoomAndOpenChatRoomUser(AuthenticatedUserThreadLocalManager.get(), openChatRoomCreateDto);

        return BaseResponseEntity.create(BaseMessageEnum.CREATED.getValue());
    }

    @Operation(summary = "오픈채팅방 입장")
    @PostMapping("/{openChatRoomId}/join")
    public ResponseEntity<BaseResponseEntity<?>> joinOpenChatRoom(@PathVariable("openChatRoomId") Long openChatRoomId, @Validated @RequestBody OpenChatRoomJoinDto openChatRoomJoinDto){
        openChatRoomFacade.joinOpenChatRoom(AuthenticatedUserThreadLocalManager.get(), openChatRoomId, openChatRoomJoinDto.getPassword());

        return BaseResponseEntity.ok(BaseMessageEnum.SUCCESS.getValue());
    }

    @Operation(summary = "오픈채팅방 입장 신청서 작정")
    @PostMapping("/{openChatRoomId}/membership-requests")
    public ResponseEntity<BaseResponseEntity<?>> createOpenChatRoomMembershipRequest(@PathVariable("openChatRoomId") Long openChatRoomId, @Validated @RequestBody OpenChatRoomMembershipRequestDto openChatRoomMembershipRequestDto){
        openChatRoomFacade.createOpenChatRoomMembershipRequest(AuthenticatedUserThreadLocalManager.get(), openChatRoomId, openChatRoomMembershipRequestDto.getMessage());

        return BaseResponseEntity.ok(BaseMessageEnum.SUCCESS.getValue());
    }

    @Operation(summary = "오픈채팅방 공지사항 수정")
    @PatchMapping("/{openChatRoomId}/notice")
    public ResponseEntity<BaseResponseEntity<?>> updateOpenChatRoomNotice(@PathVariable("openChatRoomId") Long openChatRoomId, @Validated @RequestBody OpenChatRoomNoticeUpdateDto openChatRoomNoticeUpdateDto){
        openChatRoomService.updateOpenChatRoomNotice(AuthenticatedUserThreadLocalManager.get(), openChatRoomId, openChatRoomNoticeUpdateDto.getNotice());

        return BaseResponseEntity.ok(BaseMessageEnum.SUCCESS.getValue());
    }
}
