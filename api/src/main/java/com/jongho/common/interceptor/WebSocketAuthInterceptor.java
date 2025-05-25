package com.jongho.common.interceptor;

import com.google.gson.Gson;
import com.jongho.common.exception.UnAuthorizedException;
import com.jongho.common.response.BaseResponseEntity;
import com.jongho.common.util.jwt.AccessPayload;
import com.jongho.common.util.jwt.JwtUtil;
import com.jongho.user.application.service.AuthUserService;
import com.jongho.user.application.service.UserService;
import com.jongho.user.domain.model.AuthUser;
import com.jongho.user.domain.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements HandshakeInterceptor {
    private final JwtUtil jwtUtil;
    private final AuthUserService authUserService;
    private final UserService userService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

            String accessToken = servletRequest.getHeader("Authorization");
            if (accessToken != null) {
                return handleValidAccessToken(accessToken, servletRequest, servletResponse, attributes);
            } else {
                return handleInvalidAccessToken(servletResponse, "토큰이 존재하지 않습니다.");
            }

        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }

    private boolean handleValidAccessToken(String accessToken, HttpServletRequest request, HttpServletResponse response, Map<String, Object> attributes) throws IOException {
        try {
            AccessPayload accessPayload = jwtUtil.validateAccessToken(accessToken);
            AuthUser authUser = authUserService.getAuthUser(accessPayload.getUserId()).orElseThrow(() -> new UnAuthorizedException("존재하지 않는 유저입니다."));

            User user = userService.getUserById(authUser.getUserId()).orElseThrow(() -> new UnAuthorizedException("존재하지 않는 유저입니다."));
            attributes.put("userId", user.getId());

            return true;
        } catch (UnAuthorizedException e) {

            return handleInvalidAccessToken(response, e.getMessage());
        }
    }

    private boolean handleInvalidAccessToken(HttpServletResponse response, String errorMessage) throws IOException {
        String result = new Gson().toJson(BaseResponseEntity.fail(HttpStatus.UNAUTHORIZED, errorMessage).getBody());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println(result);

        return false;
    }
}
