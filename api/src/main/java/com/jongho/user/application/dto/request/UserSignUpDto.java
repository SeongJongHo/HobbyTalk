package com.jongho.user.application.dto.request;

import com.jongho.user.domain.model.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserSignUpDto(@NotNull @NotEmpty String nickname,
                            @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "비밀번호는 8자 이상의 영문, 숫자, 특수문자 조합이어야 합니다.") @NotNull @NotEmpty String password,
                            @NotNull @NotEmpty String username,
                            @NotNull @NotEmpty String phoneNumber, @Nullable String profileImage) {



    public User toUser() {

        return new User(this.nickname, this.password, this.username, this.phoneNumber,
            this.profileImage);
    }

}
