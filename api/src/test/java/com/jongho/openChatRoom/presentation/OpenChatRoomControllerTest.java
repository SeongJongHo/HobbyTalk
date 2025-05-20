package com.jongho.openChatRoom.presentation;

import com.google.gson.Gson;
import com.jongho.common.config.WebMvcConfig;
import com.jongho.common.interceptor.AuthInterceptor;
import com.jongho.openChat.application.dto.request.OpenChatRoomCreateDto;
import com.jongho.openChat.application.facade.OpenChatRoomFacade;
import com.jongho.openChat.application.service.OpenChatRoomService;
import com.jongho.openChat.presentation.controller.OpenChatRoomController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = OpenChatRoomController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AuthInterceptor.class, WebMvcConfig.class})
)
@DisplayName("OpenChatRoomController 클래스")
public class OpenChatRoomControllerTest {
    @MockBean
    private OpenChatRoomFacade openChatRoomFacade;
    @MockBean
    private OpenChatRoomService openChatRoomService;
    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("createOpenChatRoom 메소드는")
    class Describe_createOpenChatRoom {
        private OpenChatRoomCreateDto openChatRoomCreateDto;

        @BeforeEach
        public void setUp() {
            openChatRoomCreateDto = new OpenChatRoomCreateDto(
                    "타이틀",
                    "공지사항",
                    1L,
                    200,
                    "비밀번호");
        }
        @Test
        @DisplayName("호출이 되면 status 201과 open chat room create라는 메세지를 반환한다.")
        void 호출이_되면_status_201과_open_chat_room_create라는_메세지를_반환한다() throws Exception{
            // given
            doNothing().when(openChatRoomFacade).createOpenChatRoomAndOpenChatRoomUser(1L, new OpenChatRoomCreateDto(
                    "타이틀",
                    "공지사항",
                    1L,
                    200,
                    "비밀번호"));
            Gson gson = new Gson();
            String openChatRoomCreateDtoJson = gson.toJson(
                    Map.of("title", "타이틀",
                    "notice", "공지사항",
                    "category_id", 1L,
                    "maximum_capacity", 200,
                    "password", "비밀번호"));

            // when
            mockMvc.perform(post("/api/v1/open-chat-rooms")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(openChatRoomCreateDtoJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.message").value("CREATED"))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("joinOpenChatRoom 메소드는")
    class Describe_joinOpenChatRoom {
        private String password;
        private Long openChatRoomId;

        @BeforeEach
        public void setUp() {
            password = "비밀번호";
            openChatRoomId = 1L;
        }
        @Test
        @DisplayName("호출이 되면 status 200과 success라는 메세지를 반환한다.")
        void 호출이_되면_status_200과_success라는_메세지를_반환한다() throws Exception{
            // given
            doNothing().when(openChatRoomFacade).joinOpenChatRoom(1L, 1L, "비밀번호");

            // when
            mockMvc.perform(post("/api/v1/open-chat-rooms/{openChatRoomId}/join", openChatRoomId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"password\":\"비밀번호\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("SUCCESS"))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("createOpenChatRoomMembershipRequest 메소드는")
    class Describe_createOpenChatRoomMembershipRequest {
        private String message;
        private Long openChatRoomId;
        @BeforeEach
        public void setUp() {
            message = "참가요청";
            openChatRoomId = 1L;

        }
        @Test
        @DisplayName("호출이 되면 status 200과 success라는 메세지를 반환한다.")
        void 호출이_되면_status_200과_success라는_메세지를_반환한다() throws Exception{
            // given
            doNothing().when(openChatRoomFacade).createOpenChatRoomMembershipRequest(1L, 1L, "참가요청");

            // when
            mockMvc.perform(post("/api/v1/open-chat-rooms/{openChatRoomId}/membership-requests", openChatRoomId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"message\":\"참가요청\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("SUCCESS"))
                    .andDo(print());
        }
    }
    @DisplayName("updateOpenChatRoomNotice 메소드는")
    class Describe_updateOpenChatRoomNotice {
        private String notice;
        private Long openChatRoomId;

        @BeforeEach
        public void setUp() {
            notice = "공지사항";
            openChatRoomId = 1L;
        }
        @Test
        @DisplayName("호출이 되면 status 200과 success라는 메세지를 반환한다.")
        void 호출이_되면_status_200과_success라는_메세지를_반환한다() throws Exception{
            // given
            doNothing().when(openChatRoomService).updateOpenChatRoomNotice(1L, 1L, "공지사항");

            // when
            mockMvc.perform(patch("/api/v1/open-chat-rooms/{openChatRoomId}/notice", openChatRoomId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"notice\":\"공지사항\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("SUCCESS"))
                    .andDo(print());
        }
    }
}
