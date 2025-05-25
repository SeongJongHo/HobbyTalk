package com.jongho.openChat.presentation.handler;

import static com.jongho.openChat.common.enums.ActiveTypeEnum.ACTIVE;
import static com.jongho.openChat.common.enums.ActiveTypeEnum.INACTIVE;

import com.jongho.common.database.redis.RedisService;
import com.jongho.common.util.websocket.BaseMessageTypeEnum;
import com.jongho.common.util.websocket.BaseWebSocketMessage;
import com.jongho.openChat.application.dto.response.OpenChatDto;
import com.jongho.openChat.application.facade.SendWebSocketOpenChatFacadeImpl;
import com.jongho.openChat.application.service.OpenChatRoomRedisServiceImpl;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Log4j2
@RequiredArgsConstructor
public class WebSocketOpenChatHandler extends TextWebSocketHandler {

    private final SendWebSocketOpenChatFacadeImpl sendWebSocketOpenChatFacade;
    private final OpenChatRoomRedisServiceImpl openChatRoomRedisService;
    private final RedisService redisService;
    private final String OPEN_CHAT_ROOM_CHANNEL = "openChatRoom:";

    /**
     * 웹소켓 연결이 열리고 사용자가 채팅방에 입장할 때 호출
     * @param session 웹소켓 세션
     */
    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session){
        try  {
            Long openChatRoomId = (Long) session.getAttributes().get("openChatRoomId");
            Long userId = (Long) session.getAttributes().get("userId");
            initConnectionInfoAndSubscribe(session, openChatRoomId, userId);

            session.sendMessage(new TextMessage(
                BaseWebSocketMessage.of(BaseMessageTypeEnum.JOIN, "채팅방에 입장하였습니다.")));
        } catch (Exception e) {
            handleWebSocketClose(session, e.getMessage());
        }
    }

    /**
     * 웹소켓 메시지를 처리한다.
     * @param session 웹소켓 세션
     * @param message 웹소켓 메시지
     */
    @Override
    public void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) {
        try {
            dispatchWebSocketMessage(message, session);
        } catch (Exception e) {
            handleWebSocketClose(session, e.getMessage());
        }
    }
    /**
     * 웹소켓 연결이 닫힐 때 호출
     * LastExitTime 변경해주고
     * 연결을 종료한다.
     * @param session 웹소켓 세션
     * @param status 웹소켓 연결 상태
     */
    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull org.springframework.web.socket.CloseStatus status) {
        try {
            Long openChatRoomId = (Long) session.getAttributes().get("openChatRoomId");
            Long userId = (Long) session.getAttributes().get("userId");
            openChatRoomRedisService.updateActiveChatRoom(userId, openChatRoomId, String.valueOf(INACTIVE.getType()));
            openChatRoomRedisService.updateLastExitTime(userId, openChatRoomId);

            handleWebSocketClose(session, "연결이 종료되었습니다.");
        } catch (Exception e) {
            handleWebSocketClose(session, e.getMessage());
        }
    }
    private void handleWebSocketClose(WebSocketSession session, String message) {
        try {log.error(message);
            if(session.isOpen()){
                session.close();
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 채팅장 입장 시 초기화 작업 및 채팅방 구독
     * @param session 웹소켓 세션
     * @param openChatRoomId 채팅방 아이디
     * @param userId 사용자 아이디
     */
    private void initConnectionInfoAndSubscribe(WebSocketSession session, Long openChatRoomId, Long userId) {
        openChatRoomRedisService.updateInitUnreadChatCount(userId, openChatRoomId);
        openChatRoomRedisService.updateActiveChatRoom(userId, openChatRoomId, String.valueOf(ACTIVE.getType()));
        redisService.subscribe(OPEN_CHAT_ROOM_CHANNEL + openChatRoomId, session);
    }

    private void createOpenChatAndSendMessage(OpenChatDto openChatDto, WebSocketSession session) {
        try {
            sendWebSocketOpenChatFacade.sendOpenChat(openChatDto);
            redisService.publish(
                OPEN_CHAT_ROOM_CHANNEL + openChatDto.getOpenChatRoomId(),
                BaseWebSocketMessage.of(BaseMessageTypeEnum.SEND, openChatDto));

            session.sendMessage(new TextMessage(
                BaseWebSocketMessage.of(BaseMessageTypeEnum.SEND, openChatDto)));
        } catch (IOException e) {
            log.error("웹소켓 메시지 전송 실패: {}", e.getMessage());
            handleWebSocketClose(session, e.getMessage());
        }
    }

    /**
     * 웹소켓 메시지를 분배한다.
     * @param message 웹소켓 메시지
     * @param session 웹소켓 세션
     */
    private void dispatchWebSocketMessage(TextMessage message, WebSocketSession session){
        BaseWebSocketMessage baseWebSocketMessage = redisService.convertStringMessageToBaseWebSocketMessage(message);
        switch (baseWebSocketMessage.getType()) {
            case SEND -> createOpenChatAndSendMessage(redisService.dataToObject(baseWebSocketMessage.getData(), OpenChatDto.class), session);
        }
    }
}
