package com.jongho.user.application.service;

import com.jongho.common.exception.UserDuplicatedException;
import com.jongho.common.exception.UserNotFoundException;
import com.jongho.common.util.bcrypt.BcryptUtil;
import com.jongho.user.application.dto.request.UserSignUpDto;
import com.jongho.user.domain.model.User;
import com.jongho.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public void signUp(UserSignUpDto userSignUpDto) {
        userRepository.findOneByUsername(userSignUpDto.getUsername())
                .ifPresent((user)-> {
                    throw new UserDuplicatedException("이미 존재하는 아이디입니다.");
                });

        userRepository.findOneByPhoneNumber(userSignUpDto.getPhoneNumber())
                .ifPresent((user)-> {
                    throw new UserDuplicatedException("이미 가입된 전화번호입니다.");
                });
        try {
            userRepository.createUser(
                new UserSignUpDto(
                    userSignUpDto.getNickname(),
                    BcryptUtil.hashPassword(userSignUpDto.getPassword()),
                    userSignUpDto.getUsername(),
                    userSignUpDto.getPhoneNumber(),
                    userSignUpDto.getProfileImage()
                ).toUser()
            );
        } catch (DataIntegrityViolationException e) {

            throw new UserDuplicatedException("이미 가입된 아이디거나 전화번호입니다.");
        }
    }

    @Override
    public User getUser(String username) {
        return userRepository.findOneByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 아이디입니다."));
    }
}
