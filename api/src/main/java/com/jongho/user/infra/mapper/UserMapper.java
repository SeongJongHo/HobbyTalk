package com.jongho.user.infra.mapper;

import com.jongho.user.domain.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public void createUser(User user);

    public User findOneByUsername(String username);

    public User findOneByPhoneNumber(String phoneNumber);

    public User findOneById(Long id);
}
