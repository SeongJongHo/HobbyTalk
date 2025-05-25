package com.jongho.openChat.application.facade;

import com.jongho.common.util.date.DateUtil;
import com.jongho.openChat.application.dto.response.OpenChatDto;
import com.jongho.openChat.application.service.OpenChatRedisService;
import com.jongho.openChat.application.service.OpenChatService;
import com.jongho.openChat.domain.model.OpenChat;
import com.jongho.user.application.service.UserRedisService;
import com.jongho.user.application.service.UserService;
import com.jongho.user.domain.model.User;
import com.jongho.user.domain.model.redis.CachedUserProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReadWebSocketOpenChatFacade {
    private final OpenChatService openChatService;
    private final OpenChatRedisService openChatRedisService;
    private final UserRedisService userRedisService;
    private final UserService userService;
    private final int limit = 100;

    /**
     * 최근 채팅 목록을 가져온다.
     * @param openChatRoomId 채팅방 아이디
     * @return 채팅 목록
     */
    public List<OpenChatDto> getInitialOpenChatList(Long openChatRoomId){
        List<OpenChat> openChatList = openChatRedisService.getOpenChatListByOpenChatRoomIdAndOffsetAndLimit(openChatRoomId, 0, limit-1);
        if(openChatList.isEmpty()){
            return openChatService.getOpenChatByOpenChatRoomIdAndLastCreatedTime(openChatRoomId, DateUtil.now(), limit);
        }
        List<OpenChatDto> openChatDtoList = getOpenChatDtoList(openChatList);
        if(openChatDtoList.size() >= limit){

            return openChatDtoList;
        }

        List<OpenChatDto> openChatDtos = openChatService.getOpenChatByOpenChatRoomIdAndLastCreatedTime(openChatRoomId, DateUtil.now(), limit - openChatDtoList.size());

        return getMergeOepnChatDtoList(
                openChatDtoList,
                openChatDtos
        );
    }
    /**
     * PAGINATION을 위한 채팅 목록을 가져온다.
     * @param openChatRoomId 채팅방 아이디
     * @param lastCreatedTime 마지막 생성 시간
     * @return 채팅 목록
     */
    public List<OpenChatDto> getOpenChatListByOpenChatRoomIdAndLastCreatedTime(Long openChatRoomId, String lastCreatedTime){
        List<OpenChat> openChatList = openChatRedisService.getOpenChatListByOpenChatRoomId(openChatRoomId);
        if(openChatList.isEmpty()){
            return openChatService.getOpenChatByOpenChatRoomIdAndLastCreatedTime(openChatRoomId, lastCreatedTime, limit);
        }
        List<OpenChatDto> openChatDtoList = filterOpenChatsByCreatedTime(openChatList, lastCreatedTime);
        if(openChatDtoList.size() >= limit){
            return openChatDtoList;
        }

        return getMergeOepnChatDtoList(
                openChatDtoList,
                openChatService.getOpenChatByOpenChatRoomIdAndLastCreatedTime(openChatRoomId, lastCreatedTime, limit - openChatDtoList.size())
        );
    }

    /**
     * 채팅 목록을 시간으로 필터링한다.
     * @param openChatList List<OpenChat>
     * @param lastCreatedTime 마지막 생성 시간
     * @return 채팅 목록
     */
    public List<OpenChatDto> filterOpenChatsByCreatedTime(List<OpenChat> openChatList, String lastCreatedTime){
        List<OpenChat> filterList = openChatList
                .stream()
                .filter(openChat -> DateUtil.convertStringToDate(openChat.getCreatedTime()).isAfter(DateUtil.convertStringToDate(lastCreatedTime)))
                .limit(limit)
                .toList();

        return getOpenChatDtoList(filterList);
    }

    /**
     * OpenChat을 OpenChatDto로 변환후 반환한다.
     * @param openChatList List<OpenChat>
     * @return 채팅 목록
     */
    private List<OpenChatDto> getOpenChatDtoList(List<OpenChat> openChatList){
        return openChatList
                .stream()
                .map(this::convertOpenChatToOpenChatDto)
                .toList();
    }

    /**
     * OpenChat을 OpenChatDto로 변환한다.
     * @param openChat OpenChat
     * @return OpenChatDto
     */
    private OpenChatDto convertOpenChatToOpenChatDto(OpenChat openChat) {
        CachedUserProfile cachedUserProfile = userRedisService.getUserProfileByUserId(openChat.getSenderId());
        OpenChatDto openChatDto = new OpenChatDto(
                openChat.getId(),
                openChat.getOpenChatRoomId(),
                openChat.getMessage(),
                openChat.getType(),
                openChat.getIsDeleted(),
                openChat.getDeletedTime(),
                openChat.getCreatedTime()
        );
        if (cachedUserProfile != null) {
            openChatDto.setSenderProfile(cachedUserProfile);
        }
        else{
            openChatDto.setSenderProfile(getSenderProfile(openChat.getSenderId(), openChatDto));
        }

        return openChatDto;
    }

    /**
     * 캐시에 없는 유저의 프로필을 가져온다.
     * @param senderId 유저 아이디
     * @param openChatDto OpenChatDto
     * @return CachedUserProfile
     */
    private CachedUserProfile getSenderProfile(Long senderId, OpenChatDto openChatDto) {
            Optional<User> optionalUser = userService.getUserById(senderId);
        return optionalUser
                .map(user ->{
                    userRedisService.createUserProfileByUserId(user.getId(), user.getNickname(), user.getProfileImage());
                    return new CachedUserProfile(user.getId(), user.getNickname(), user.getProfileImage());
                })
                .orElseGet(() -> new CachedUserProfile(null, "unknown", "unknown"));
    }

    /**
     * 두개의 OpenChatDto 리스트를 합친다.
     * @param openChatDtoListFromRedis List<OpenChatDto>
     * @param openChatDtoList List<OpenChatDto>
     * @return List<OpenChatDto>
     */
    private List<OpenChatDto> getMergeOepnChatDtoList(List<OpenChatDto> openChatDtoListFromRedis, List<OpenChatDto> openChatDtoList){
        return Stream.concat(openChatDtoListFromRedis.stream(), openChatDtoList.stream())
                .toList();
    }
}
