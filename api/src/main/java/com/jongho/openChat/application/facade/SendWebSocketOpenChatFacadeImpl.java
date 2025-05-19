package com.jongho.openChat.application.facade;

import com.jongho.common.exception.OpenChatRoomNotFoundException;
import com.jongho.openChat.application.dto.response.OpenChatDto;
import com.jongho.openChat.application.service.OpenChatRedisService;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.openChat.application.service.OpenChatRoomRedisService;
import com.jongho.openChat.application.service.OpenChatRoomService;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChat.domain.model.redis.CachedOpenChatRoomConnectionInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jongho.openChat.common.enums.ActiveTypeEnum.INACTIVE;

@Service
@Log4j2
@RequiredArgsConstructor
public class SendWebSocketOpenChatFacadeImpl implements SendWebSocketOpenChatFacade{
    private final OpenChatRedisService openChatRedisService;
    private final OpenChatRoomRedisService openChatRoomRedisService;
    private final OpenChatRoomService openChatRoomService;

    /**
     * 채팅방에 채팅을 전송한다.
     * @param openChatDto 새로 생성된 채팅 정보
     */
    @Override
    public void sendOpenChat(OpenChatDto openChatDto){
        OpenChat openChat = convertToOpenChat(openChatDto);
        openChatRedisService.createOpenChat(openChat);
        openChatRedisService.updateLastOpenChat(openChat);
        increaseUnreadMessageCountForAllUsersInChatRoom(openChat);
    }

    /**
     * 채팅방의 유저들의 안읽은 메세지를 증가시키는 메소드
     * @param openChat 새로 생성된 채팅
     */
    private void increaseUnreadMessageCountForAllUsersInChatRoom(OpenChat openChat) {
        getChatRoomUserList(openChat.getOpenChatRoomId())
                .forEach(userId -> increaseUnreadMessageCountForInactiveChatRoom(userId, openChat.getOpenChatRoomId()));
    }

    /**
     * Cache or DB에서 채팅방의 유저 리스트를 가져오는 메소드
     * @param openChatRoomId 채팅방 ID
     * @return 채팅방의 유저 리스트
     */
    private List<Long> getChatRoomUserList(Long openChatRoomId) {
        CachedOpenChatRoom cachedOpenChatRoom = getOpenChatRoom(openChatRoomId);
        List<Long> userIdList = openChatRoomRedisService.getOpenChatRoomUserList(openChatRoomId);
        if(cachedOpenChatRoom.getCurrentAttendance() != userIdList.size()){
            userIdList = openChatRoomService.getOpenChatRoomUserList(openChatRoomId);
        }
        return  userIdList;
    }

    /**
     * Cache or DB에서 채팅방을 가져오는 메소드
     * @param openChatRoomId 채팅방 ID
     * @return 채팅방
     */
    private CachedOpenChatRoom getOpenChatRoom(Long openChatRoomId) {
        return openChatRoomRedisService.getOpenChatRoom(openChatRoomId)
                .orElseGet(()-> openChatRoomService.getRedisOpenChatRoomById(openChatRoomId)
                        .orElseThrow(()-> new OpenChatRoomNotFoundException("채팅방을 찾을 수 없습니다.")));
    }

    /**
     * Cache 에서 채팅방의 유저의 연결 정보를 가져오는 메소드
     * @param userId 사용자 ID
     * @param openChatRoomId 채팅방 ID
     * @return 채팅방의 유저의 연결 정보
     */
    private CachedOpenChatRoomConnectionInfo getOpenChatRoomConnectionInfo(Long userId, Long openChatRoomId) {
        return openChatRoomRedisService.getRedisOpenChatRoomConnectionInfo(userId, openChatRoomId);
    }

    /**
     * 채팅방의 안읽은 메세지를 증가시키는 메소드
     * @param userId 사용자 ID
     * @param openChatRoomId 채팅방 ID
     */
    public void increaseUnreadMessageCountForInactiveChatRoom(Long userId, Long openChatRoomId) {
        CachedOpenChatRoomConnectionInfo cachedOpenChatRoomConnectionInfo = getOpenChatRoomConnectionInfo(userId, openChatRoomId);
        if (cachedOpenChatRoomConnectionInfo != null && cachedOpenChatRoomConnectionInfo.getActive() == INACTIVE.getType()) {
            openChatRoomRedisService.incrementUnreadMessageCount(userId, openChatRoomId);
        }
    }

    /**
     * OpenChatDto를 OpenChat으로 변환하는 메소드
     * @param openChatDto OpenChatDto
     * @return OpenChat
     *
     */
    private OpenChat convertToOpenChat(OpenChatDto openChatDto) {
        return new OpenChat(
                openChatDto.getId(),
                openChatDto.getSenderProfile().getId(),
                openChatDto.getOpenChatRoomId(),
                openChatDto.getMessage(),
                openChatDto.getType(),
                openChatDto.getIsDeleted(),
                openChatDto.getDeletedTime(),
                openChatDto.getCreatedTime());
    }
}
