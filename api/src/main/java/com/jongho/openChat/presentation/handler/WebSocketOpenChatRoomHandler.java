package com.jongho.openChat.presentation.handler;

import com.jongho.common.database.redis.RedisService;
import com.jongho.common.util.websocket.BaseMessageTypeEnum;
import com.jongho.common.util.websocket.BaseWebSocketMessage;
import com.jongho.openChat.application.dto.response.OpenChatRoomDto;
import com.jongho.openChat.application.facade.WebSocketOpenChatRoomFacade;
import java.io.IOException;
import java.util.List;
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
public class WebSocketOpenChatRoomHandler extends TextWebSocketHandler {

    private final WebSocketOpenChatRoomFacade webSocketOpenChatRoomFacade;
    private final RedisService redisService;
    private final String OPEN_CHAT_ROOM_CHANNEL = "openChatRoom:";
    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session){
        try  {
            List<OpenChatRoomDto> openChatRoomDto = webSocketOpenChatRoomFacade.getOpenChatRoomList((long) session.getAttributes().get("userId"));
            redisService.subscribe(getChannelList(openChatRoomDto), session);

            session.sendMessage(
                    new TextMessage(BaseWebSocketMessage.of(BaseMessageTypeEnum.PAGINATION, openChatRoomDto)));
        } catch (Exception e) {
            log.error(e.getMessage());
            handleWebSocketClose(session);
        }
    }

    private void handleWebSocketClose(WebSocketSession session) {
        try {
            session.close();
        } catch (IOException e) {
            log.error("session.close");
        }
    }

    private List<String> getChannelList(List<OpenChatRoomDto> openChatRoomDtoList) {
        return openChatRoomDtoList
                .stream()
                .map(openChatRoomDto -> {
                    return OPEN_CHAT_ROOM_CHANNEL + openChatRoomDto.getId();
                }).toList();
    }
}
