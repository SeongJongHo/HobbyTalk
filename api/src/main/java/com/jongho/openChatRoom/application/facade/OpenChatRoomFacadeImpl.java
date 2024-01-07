package com.jongho.openChatRoom.application.facade;

import com.jongho.category.application.service.CategoryService;
import com.jongho.category.domain.model.Category;
import com.jongho.common.exception.*;
import com.jongho.openChatRoom.application.dto.request.OpenChatRoomCreateDto;
import com.jongho.openChatRoom.application.service.OpenChatRoomService;
import com.jongho.openChatRoom.domain.model.OpenChatRoom;
import com.jongho.openChatRoomUser.application.service.OpenChatRoomUserService;
import com.jongho.openChatRoomUser.domain.model.OpenChatRoomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.AlreadyBoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenChatRoomFacadeImpl implements OpenChatRoomFacade {
    private final OpenChatRoomService openChatRoomService;
    private final OpenChatRoomUserService openChatRoomUserService;
    private final CategoryService categoryService;
    private final int MAXIMUM_OPEN_CHAT_ROOM_COUNT = 5;
    @Override
    @Transactional
    public void createOpenChatRoomAndOpenChatRoomUser(Long authUserId, OpenChatRoomCreateDto openChatRoomCreateDto) {
        if(openChatRoomService.getOpenChatRoomCountByManagerId(authUserId) >= MAXIMUM_OPEN_CHAT_ROOM_COUNT){
            throw new MaxChatRoomsExceededException("최대 개설 가능한 채팅방 개수를 초과하였습니다.");
        }
        Optional<Category> category = categoryService.getOneCategoryById(openChatRoomCreateDto.getCategoryId());
        if(category.isEmpty()){
            throw new CategoryNotFoundException("존재하지 않는 카테고리입니다.");
        }
        Optional<OpenChatRoom> openChatRoomByManagerIdAndTitle = openChatRoomService.getOpenChatRoomByManagerIdAndTitle(authUserId, openChatRoomCreateDto.getTitle());
        if(openChatRoomByManagerIdAndTitle.isPresent()){
            throw new AlreadyExistsException("이미 존재하는 채팅방입니다.");
        }

        OpenChatRoom openChatRoom = new OpenChatRoom(
                openChatRoomCreateDto.getTitle(),
                openChatRoomCreateDto.getNotice(),
                authUserId,
                openChatRoomCreateDto.getCategoryId(),
                openChatRoomCreateDto.getMaximumCapacity(),
                openChatRoomCreateDto.getPassword()
        );
        openChatRoomService.createOpenChatRoom(openChatRoom);
        openChatRoomUserService.createOpenChatRoomUser(new OpenChatRoomUser(openChatRoom.getId(), authUserId));
    }
    @Override
    @Transactional
    public void joinOpenChatRoom(Long authUserId, Long openChatRoomId, String password) {
        OpenChatRoom openChatRoom = openChatRoomService.getOpenChatRoomById(openChatRoomId)
                .orElseThrow(()-> new OpenChatRoonNotFoundException("존재하지 않는 채팅방입니다."));
        if(openChatRoom.getPassword() != null){
            if(!openChatRoom.getPassword().equals(password)){
                throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
            }
        }
        if(openChatRoom.getMaximumCapacity() <= openChatRoom.getCurrentAttendance()){
            throw new MaxChatRoomsJoinException("채팅방의 최대 인원을 초과하여 입장할 수 없습니다.");
        }
        Optional<OpenChatRoomUser> openChatRoomUser = openChatRoomUserService.getOpenChatRoomUserByOpenChatRoomIdAndUserId(openChatRoomId, authUserId);
        if(openChatRoomUser.isPresent()){
            throw new AlreadyExistsException("이미 참여중인 채팅방입니다.");
        }
        openChatRoomUserService.createOpenChatRoomUser(new OpenChatRoomUser(openChatRoomId, authUserId));
        openChatRoomService.incrementOpenChatRoomCurrentAttendance(openChatRoomId, openChatRoom.getCurrentAttendance());
    }
}
