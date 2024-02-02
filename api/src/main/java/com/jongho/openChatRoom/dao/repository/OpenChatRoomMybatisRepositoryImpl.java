package com.jongho.openChatRoom.dao.repository;

import com.jongho.openChatRoom.dao.mapper.OpenChatRoomMapper;
import com.jongho.openChatRoom.domain.model.OpenChatRoom;
import com.jongho.openChatRoom.domain.model.redis.CachedOpenChatRoom;
import com.jongho.openChatRoom.domain.repository.OpenChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OpenChatRoomMybatisRepositoryImpl implements OpenChatRoomRepository {
    private final OpenChatRoomMapper openChatRoomMapper;
    @Override
    public int countByManagerId(Long managerId) {
        return openChatRoomMapper.countByManagerId(managerId);
    }
    @Override
    public void createOpenChatRoom(OpenChatRoom openChatRoom) {
        openChatRoomMapper.createOpenChatRoom(openChatRoom);
    }
    @Override
    public void updateIncrementCurrentCapacity(Long openChatRoomId, int currentAttendance) {
        openChatRoomMapper.updateIncrementCurrentCapacity(openChatRoomId, currentAttendance);
    }
    @Override
    public Optional<OpenChatRoom> selectOneOpenChatRoomByManagerIdAndTitle(Long managerId, String title) {
        return Optional.ofNullable(openChatRoomMapper.selectOneOpenChatRoomByManagerIdAndTitle(managerId, title));
    }
    @Override
    public Optional<OpenChatRoom> selectOneOpenChatRoomByIdForUpdate(Long openChatRoomId) {
        return Optional.ofNullable(openChatRoomMapper.selectOneOpenChatRoomByIdForUpdate(openChatRoomId));
    }
    @Override
    public Optional<OpenChatRoom> selectOneOpenChatRoomById(Long openChatRoomId) {
        return Optional.ofNullable(openChatRoomMapper.selectOneOpenChatRoomById(openChatRoomId));
    }
    @Override
    public void updateOpenChatRoomNotice(Long openChatRoomId, String notice) {
        openChatRoomMapper.updateOpenChatRoomNotice(openChatRoomId, notice);
    }
    @Override
    public List<CachedOpenChatRoom> selectJoinOpenChatRoomByUserId(Long userId){
        return openChatRoomMapper.selectJoinOpenChatRoomByUserId(userId);
    };
    @Override
    public List<Long> selectOpenChatRoomUser(Long openChatRoomId){
        return openChatRoomMapper.selectOpenChatRoomUser(openChatRoomId);
    };
    @Override
    public Optional<CachedOpenChatRoom> selectRedisOpenChatRoomById(Long openChatRoomId){
        return Optional.ofNullable(openChatRoomMapper.selectRedisOpenChatRoomById(openChatRoomId));
    };
}
