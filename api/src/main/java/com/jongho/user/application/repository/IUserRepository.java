package com.jongho.user.application.repository;

import com.jongho.user.domain.model.User;

import java.util.Optional;

public interface IUserRepository {
    public void createUser(User user);

    public Optional<User> findOneByUsername(String username);

    public Optional<User> findOneByPhoneNumber(String phoneNumber);

    public Optional<User> findOneById(Long id);
}
