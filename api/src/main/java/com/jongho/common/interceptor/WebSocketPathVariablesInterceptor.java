package com.jongho.common.interceptor;

import com.google.gson.Gson;
import com.jongho.common.response.BaseResponseEntity;
import com.jongho.openChat.application.service.OpenChatRoomService;
import com.jongho.openChat.application.service.OpenChatRoomUserService;
import com.jongho.openChat.domain.model.OpenChatRoom;
import com.jongho.openChat.domain.model.OpenChatRoomUser;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
@Log4j2
@RequiredArgsConstructor
public class WebSocketPathVariablesInterceptor implements HandshakeInterceptor {

    private final OpenChatRoomService openChatRoomService;
    private final OpenChatRoomUserService openChatRoomUserService;

    /**
     * WebSocket 연결 전에
     * 내 채팅방 리스트에 연결되는거라면 바로 통과
     * 특정 채팅방에 연결되는거라면
     * 1. 채팅방이 존재하는지 확인
     * 2. 사용자가 채팅방에 참여하고 있는지 확인
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            String OPEN_CHAT_ROOM_ENDPOIND = "/open-chat-rooms";
            HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
            String path = servletRequest.getURI().getPath();
            if(path.equals(OPEN_CHAT_ROOM_ENDPOIND)){
                return true;
            }

            String[] pathVariables = path.split("/");
            Long openChatRoomId;
            try {
                openChatRoomId = Long.parseLong(pathVariables[pathVariables.length - 2]);
            } catch (NumberFormatException e) {
                log.error("400 Bad Request chatRoomId: {}", pathVariables[pathVariables.length - 2]);
                return handleInvalidChatRoom(servletResponse, "올바르지 않는 url입니다.", HttpStatus.BAD_REQUEST);
            }
            Optional<OpenChatRoom> openChatRoom = openChatRoomService.getOpenChatRoomById(openChatRoomId);
            if (openChatRoom.isEmpty()) {
                log.error("404 Not Foud chatRoomId: {}", openChatRoomId);
                return handleInvalidChatRoom(servletResponse, "존재하지 않는 채팅방입니다.", HttpStatus.NOT_FOUND);
            }
            Optional<OpenChatRoomUser> userId = openChatRoomUserService.getOpenChatRoomUserByOpenChatRoomIdAndUserId(openChatRoom.get().getId(), (Long) attributes.get("userId"));
            if (userId.isEmpty()) {
                log.error("404 Not Foud chatRoomId: {}, userId: {}", openChatRoomId, attributes.get("userId"));
                return handleInvalidChatRoom(servletResponse, "채팅방에 참여하지 않은 사용자입니다.", HttpStatus.NOT_FOUND);
            }
            attributes.put("openChatRoomId", openChatRoom.get().getId());
        }

        return true;
    }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
    }

    private boolean handleInvalidChatRoom(HttpServletResponse response, String errorMessage, HttpStatus httpStatus) throws IOException {
        String result = new Gson().toJson(BaseResponseEntity.fail(httpStatus, errorMessage).getBody());
        response.setStatus(httpStatus.value());
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println(result);

        return false;
    }
}
