package com.jongho.common.interceptor;

import com.google.gson.Gson;
import com.jongho.common.response.BaseResponseEntity;
import com.jongho.openChatRoom.application.service.OpenChatRoomService;
import com.jongho.openChatRoom.domain.model.OpenChatRoom;
import com.jongho.openChatRoomUser.application.service.OpenChatRoomUserService;
import com.jongho.openChatRoomUser.domain.model.OpenChatRoomUser;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WebSocketPathVariablesInterceptor implements HandshakeInterceptor {
    private final OpenChatRoomService openChatRoomService;
    private final OpenChatRoomUserService openChatRoomUserService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
            String path = servletRequest.getURI().getPath();
            String[] pathVariables = path.split("/");
            if (pathVariables.length < 2) {
                return true;
            }

            Long openChatRoomId = Long.parseLong(pathVariables[pathVariables.length - 2]);
            Optional<OpenChatRoom> openChatRoom = openChatRoomService.getOpenChatRoomById(openChatRoomId);
            if (openChatRoom.isEmpty()) {
                return handleInvalidChatRoom(servletResponse, "존재하지 않는 채팅방입니다.");
            }
            Optional<OpenChatRoomUser> userId = openChatRoomUserService.getOpenChatRoomUserByOpenChatRoomIdAndUserId(openChatRoom.get().getId(), (Long) attributes.get("userId"));
            if (userId.isEmpty()) {
                return handleInvalidChatRoom(servletResponse, "채팅방에 참여하지 않은 사용자입니다.");
            }
            attributes.put("openChatRoomId", openChatRoom.get().getId());
        }

        return true;
    }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
    }

    private boolean handleInvalidChatRoom(HttpServletResponse response, String errorMessage) throws IOException {
        String result = new Gson().toJson(BaseResponseEntity.fail(HttpStatus.NOT_FOUND, errorMessage).getBody());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println(result);

        return false;
    }
}
