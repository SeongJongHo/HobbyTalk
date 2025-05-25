package com.jongho.user.infra.repository;

import com.jongho.user.infra.mapper.UserMapper;
import com.jongho.user.domain.model.User;
import com.jongho.user.application.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {
    private final UserMapper userMapper;

    @Override
    public void createUser(User user) {
        userMapper.createUser(user);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return Optional.ofNullable(userMapper.findOneByUsername(username));
    }

    @Override
    public Optional<User> findOneByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(userMapper.findOneByPhoneNumber(phoneNumber));
    }

    @Override
    public Optional<User> findOneById(Long id) {
        return Optional.ofNullable(userMapper.findOneById(id));
    }
}
