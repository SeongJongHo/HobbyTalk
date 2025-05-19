package com.jongho.openChatRoom.dao.mapper;

import com.jongho.common.dao.BaseMapperTest;
import com.jongho.openChat.dao.mapper.OpenChatRoomMapper;
import com.jongho.openChat.domain.model.OpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChatRoomUser.dao.mapper.OpenChatRoomUserMapper;
import com.jongho.openChatRoomUser.domain.model.OpenChatRoomUser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("OpenChatRoomMapper 인터페이스")
public class OpenChatRoomMapperTest extends BaseMapperTest {
    @Autowired
    private OpenChatRoomMapper openChatRoomMapper;
    @Autowired
    private OpenChatRoomUserMapper openChatRoomUserMapper;

    @Nested
    @DisplayName("createOpenChatRoom 메소드는")
    class Describe_createOpenChatRoom {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomTable();
        }

        @Test
        @DisplayName("인자로 받은 오픈채팅방을 저장한다.")
        void 오픈채팅방을_생성한다() {
            // given
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );

            // when
            openChatRoomMapper.createOpenChatRoom(openChatRoom);

            // then
            assertEquals(1, openChatRoom.getId());
        }
    }

    @Nested
    @DisplayName("countByManagerId 메소드는")
    class Describe_countByManagerId {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomTable();
        }

        @Test
        @DisplayName("인자로 받은 매니저 아이디를 가진 오픈채팅방의 개수를 반환한다.")
        void 오픈채팅방의_개수를_반환한다() {
            // given
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            openChatRoomMapper.createOpenChatRoom(openChatRoom);

            // when
            int count = openChatRoomMapper.countByManagerId(openChatRoom.getManagerId());

            // then
            assertEquals(1, count);
        }
    }

    @Nested
    @DisplayName("updateIncrementCurrentCapacity 메소드는")
    class Describe_updateIncrementCurrentCapacity {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomTable();
        }

        @Test
        @DisplayName("인자로 받은 오픈채팅방의 현재 참여 인원을 1 증가시킨다.")
        void 오픈채팅방의_현재_참여_인원을_1_증가시킨다() {
            // given
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            openChatRoomMapper.createOpenChatRoom(openChatRoom);

            // when
            openChatRoomMapper.updateIncrementCurrentCapacity(openChatRoom.getId(), 1);

            // then
            assertEquals(2, openChatRoomMapper.selectOneOpenChatRoomByIdForUpdate(openChatRoom.getId()).getCurrentAttendance());
        }
    }

    @Nested
    @DisplayName("selectOneOpenChatRoomByManagerIdAndTitle 메소드는")
    class Describe_selectOneOpenChatRoomByManagerIdAndTitle {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomTable();
        }

        @Test
        @DisplayName("인자로 받은 매니저 아이디와 타이틀을 가진 오픈채팅방을 반환한다.")
        void 오픈채팅방을_반환한다() {
            // given
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            openChatRoomMapper.createOpenChatRoom(openChatRoom);

            // when
            OpenChatRoom result = openChatRoomMapper.selectOneOpenChatRoomByManagerIdAndTitle(openChatRoom.getManagerId(), openChatRoom.getTitle());

            // then
            assertEquals(openChatRoom.getId(), result.getId());
        }
    }

    @Nested
    @DisplayName("selectOneOpenChatRoomByIdForUpdate 메소드는")
    class Describe_selectOneOpenChatRoomByIdForUpdate {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomTable();
        }

        @Test
        @DisplayName("인자로 받은 오픈채팅방 아이디를 가진 오픈채팅방을 반환한다.")
        void 오픈채팅방을_반환한다() {
            // given
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            openChatRoomMapper.createOpenChatRoom(openChatRoom);

            // when
            OpenChatRoom result = openChatRoomMapper.selectOneOpenChatRoomByIdForUpdate(openChatRoom.getId());

            // then
            assertEquals(openChatRoom.getId(), result.getId());
        }
    }

    @Nested
    @DisplayName("selectOneOpenChatRoomById 메소드는")
    class Describe_selectOneOpenChatRoomById {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomTable();
        }

        @Test
        @DisplayName("인자로 받은 오픈채팅방 아이디를 가진 오픈채팅방을 반환한다.")
        void 오픈채팅방을_반환한다() {
            // given
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            openChatRoomMapper.createOpenChatRoom(openChatRoom);

            // when
            OpenChatRoom result = openChatRoomMapper.selectOneOpenChatRoomById(openChatRoom.getId());

            // then
            assertEquals(openChatRoom.getId(), result.getId());
        }
    }

    @Nested
    @DisplayName("updateOpenChatRoomNotice 메소드는")
    class Describe_updateOpenChatRoomNotice {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomTable();
        }

        @Test
        @DisplayName("인자로 받은 오픈채팅방 아이디를 가진 오픈채팅방의 공지사항을 수정한다.")
        void 오픈채팅방의_공지사항을_수정한다() {
            // given
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀",
                    "공지사항",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            openChatRoomMapper.createOpenChatRoom(openChatRoom);

            // when
            openChatRoomMapper.updateOpenChatRoomNotice(openChatRoom.getId(), "수정된 공지사항");

            // then
            assertEquals("수정된 공지사항", openChatRoomMapper.selectOneOpenChatRoomById(openChatRoom.getId()).getNotice());
        }
    }
    @Nested
    @DisplayName("selectJoinOpenChatRoomByUserId 메소드는")
    class Describe_selectJoinOpenChatRoomByUserId {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomTable();
            cleanUpOpenChatRoomUserTable();
        }

        @Test
        @DisplayName("인자로 받은 유저 아이디를 가진 오픈채팅방유저를 반환한다.")
        void 오픈채팅방유저_리스트를_반환한다() {
            // given
            OpenChatRoom openChatRoom1 = new OpenChatRoom(
                    "타이틀1",
                    "공지사항1",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            OpenChatRoom openChatRoom2 = new OpenChatRoom(
                    "타이틀2",
                    "공지사항2",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            openChatRoomMapper.createOpenChatRoom(openChatRoom1);
            openChatRoomMapper.createOpenChatRoom(openChatRoom2);
            openChatRoomUserMapper.createOpenChatRoomUser(new OpenChatRoomUser(openChatRoom1.getId(), 1L));
            openChatRoomUserMapper.createOpenChatRoomUser(new OpenChatRoomUser(openChatRoom2.getId(), 1L));

            // when
            var result = openChatRoomMapper.selectJoinOpenChatRoomByUserId(1L);

            // then
            assertEquals(2, result.size());
        }
    }
    @Nested
    @DisplayName("selectOpenChatRoomUser 메소드는")
    class Describe_selectOpenChatRoomUser {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomTable();
            cleanUpOpenChatRoomUserTable();
        }

        @Test
        @DisplayName("인자로 받은 오픈채팅방 아이디를 가진 오픈채팅방유저를 반환한다.")
        void 오픈채팅방유저_리스트를_반환한다() {
            // given
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀1",
                    "공지사항1",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            openChatRoomMapper.createOpenChatRoom(openChatRoom);
            openChatRoomUserMapper.createOpenChatRoomUser(new OpenChatRoomUser(openChatRoom.getId(), 1L));
            openChatRoomUserMapper.createOpenChatRoomUser(new OpenChatRoomUser(openChatRoom.getId(), 2L));
            openChatRoomUserMapper.createOpenChatRoomUser(new OpenChatRoomUser(openChatRoom.getId(), 3L));

            // when
            var result = openChatRoomMapper.selectOpenChatRoomUser(openChatRoom.getId());

            // then
            assertEquals(3, result.size());
        }
    }
    @Nested
    @DisplayName("selectRedisOpenChatRoomById 메소드는")
    class Describe_selectCachedOpenChatRoomById {
        @BeforeEach
        void setUp() {
            cleanUpOpenChatRoomTable();
            cleanUpOpenChatRoomUserTable();
        }

        @Test
        @DisplayName("인자로 받은 오픈채팅방 아이디를 가진 오픈채팅방을 반환한다.")
        void 오픈채팅방을_반환한다() {
            // given
            OpenChatRoom openChatRoom = new OpenChatRoom(
                    "타이틀1",
                    "공지사항1",
                    1L,
                    1L,
                    200,
                    "비밀번호"
            );
            openChatRoomMapper.createOpenChatRoom(openChatRoom);

            // when
            var result = openChatRoomMapper.selectRedisOpenChatRoomById(openChatRoom.getId());

            // then
            assertNotNull(result.getCreatedTime());
            assertEquals(CachedOpenChatRoom.class, result.getClass());
        }
    }
}
