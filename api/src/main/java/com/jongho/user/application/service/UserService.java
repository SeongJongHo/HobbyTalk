package com.jongho.user.application.service;

import com.jongho.common.exception.UserDuplicatedException;
import com.jongho.common.exception.UserNotFoundException;
import com.jongho.common.util.bcrypt.BcryptUtil;
import com.jongho.user.application.dto.request.UserSignUpDto;
import com.jongho.user.application.repository.IUserRepository;
import com.jongho.user.domain.model.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final IUserRepository IUserRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void signUp(UserSignUpDto userSignUpDto) {
        IUserRepository.findOneByUsername(userSignUpDto.username())
                .ifPresent((user)-> {
                    throw new UserDuplicatedException("이미 존재하는 아이디입니다.");
                });

        IUserRepository.findOneByPhoneNumber(userSignUpDto.phoneNumber())
                .ifPresent((user)-> {
                    throw new UserDuplicatedException("이미 가입된 전화번호입니다.");
                });
        try {
            IUserRepository.createUser(
                new UserSignUpDto(
                    userSignUpDto.nickname(),
                    BcryptUtil.hashPassword(userSignUpDto.password()),
                    userSignUpDto.username(),
                    userSignUpDto.phoneNumber(),
                    userSignUpDto.profileImage()
                ).toUser()
            );
        } catch (DataIntegrityViolationException e) {

            throw new UserDuplicatedException("이미 가입된 아이디거나 전화번호입니다.");
        }
    }

    public User getUser(String username) {
        return IUserRepository.findOneByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 아이디입니다."));
    }

    public Optional<User> getUserById(Long id) {
        return IUserRepository.findOneById(id);
    }
}
