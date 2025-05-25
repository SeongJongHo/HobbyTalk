package com.jongho.openChatRoomMembershipRequest.dao.mapper;

import com.jongho.openChat.common.enums.MembershipRequestStatusEnum;
import com.jongho.openChat.infra.mapper.OpenChatRoomMembershipRequestMapper;
import com.jongho.openChat.domain.model.OpenChatRoomMembershipRequest;
import com.jongho.common.dao.BaseMapperTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenChatRoomMembershipRequestMapperTest extends BaseMapperTest {
    @Autowired
    private OpenChatRoomMembershipRequestMapper openChatRoomMembershipRequestMapper;

    @Nested
    @DisplayName("countByRequesterIdAndStatus 메소드는")
    class Describe_countByRequesterIdAndStatus {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomMembershipRequestTable();
        }

        @Test
        @DisplayName("인자로 받은 요청자 아이디와 상태를 가진 오픈채팅방 멤버쉽 요청의 개수를 반환한다.")
        void 오픈채팅방_멤버쉽_요청의_개수를_반환한다() {
            // given
            OpenChatRoomMembershipRequest openChatRoomMembershipRequest = new OpenChatRoomMembershipRequest(
                    1L
                    , 1L
                    , "참가요청"
                    , MembershipRequestStatusEnum.PARTICIPATION_REQUEST.getValue()
            );

            // when
            openChatRoomMembershipRequestMapper.createOpenChatRoomMembershipRequest(openChatRoomMembershipRequest);

            // then
            assertEquals(1, openChatRoomMembershipRequest.getId());
        }

        @Nested
        @DisplayName("existsByRequesterIdAndOpenChatRoomIdAndStatus 메소드는")
        class Describe_existsByRequesterIdAndOpenChatRoomIdAndStatus {
            @BeforeEach
            void setUp() {
                cleanUpOpenChatRoomMembershipRequestTable();
            }

            @Test
            @DisplayName("인자로 받은 요청자 아이디와 오픈채팅방 아이디, 상태를 가진 오픈채팅방 멤버쉽 요청이 존재하는지 여부를 반환한다.")
            void 오픈채팅방_멤버쉽_요청이_존재하는지_여부를_반환한다() {
                // given
                OpenChatRoomMembershipRequest openChatRoomMembershipRequest = new OpenChatRoomMembershipRequest(
                        1L
                        , 1L
                        , "참가요청"
                        , MembershipRequestStatusEnum.PARTICIPATION_REQUEST.getValue()
                );
                openChatRoomMembershipRequestMapper.createOpenChatRoomMembershipRequest(openChatRoomMembershipRequest);

                // when
                boolean result = openChatRoomMembershipRequestMapper.existsByRequesterIdAndOpenChatRoomIdAndStatus(1L, 1L, MembershipRequestStatusEnum.PARTICIPATION_REQUEST.getValue());

                // then
                assertTrue(result);
            }
        }

        @Nested
        @DisplayName("createOpenChatRoomMembershipRequest 메소드는")
        class Describe_createOpenChatRoomMembershipRequest {
            @BeforeEach
            void setUp() {
                cleanUpOpenChatRoomMembershipRequestTable();
            }

            @Test
            @DisplayName("인자로 받은 오픈채팅방 멤버쉽 요청을 저장한다.")
            void 오픈채팅방_멤버쉽_요청을_생성한다() {
                // given
                OpenChatRoomMembershipRequest openChatRoomMembershipRequest = new OpenChatRoomMembershipRequest(
                        1L
                        , 1L
                        , "참가요청"
                        , MembershipRequestStatusEnum.PARTICIPATION_REQUEST.getValue()
                );

                // when
                openChatRoomMembershipRequestMapper.createOpenChatRoomMembershipRequest(openChatRoomMembershipRequest);

                // then
                assertEquals(1, openChatRoomMembershipRequest.getId());
            }
        }
    }
}
