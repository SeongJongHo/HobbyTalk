package com.jongho.openChatRoomUser.dao.mapper;

import com.jongho.common.dao.BaseMapperTest;
import com.jongho.openChat.infra.mapper.OpenChatRoomUserMapper;
import com.jongho.openChat.domain.model.OpenChatRoomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("OpenChatRoomUserMapper 인터페이스")
public class OpenChatRoomUserMapperTest extends BaseMapperTest {
    @Autowired
    private OpenChatRoomUserMapper openChatRoomUserMapper;

    @Nested
    @DisplayName("createOpenChatRoomUser 메소드는")
    class Describe_createOpenChatRoomUser {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomUserTable();
        }

        @Test
        @DisplayName("인자로 받은 오픈채팅방 유저를 저장한다.")
        void 오픈채팅방_유저를_생성한다() {
            // given
            OpenChatRoomUser openChatRoomUser = new OpenChatRoomUser(
                    1L,
                    2L
            );
            openChatRoomUserMapper.createOpenChatRoomUser(openChatRoomUser);

            // when

            OpenChatRoomUser selectOpenChatRoomUser = openChatRoomUserMapper.selectOneOpenChatRoomUserByOpenChatRoomIdAndUserId(
                    openChatRoomUser.getOpenChatRoomId(),
                    openChatRoomUser.getUserId());
            // then
            assertEquals(openChatRoomUser.getUserId(), selectOpenChatRoomUser.getUserId());
            assertEquals(openChatRoomUser.getOpenChatRoomId(), selectOpenChatRoomUser.getOpenChatRoomId());
        }
    }
}
