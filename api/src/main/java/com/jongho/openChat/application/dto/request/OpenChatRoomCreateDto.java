package com.jongho.openChat.application.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OpenChatRoomCreateDto {
    @NotNull
    @NotEmpty
    private final String title;
    @Nullable
    private final String notice;
    @NotNull
    private final Long categoryId;
    @NotNull
    private final int maximumCapacity;
    @Nullable
    private final String password;
    @JsonCreator
    public OpenChatRoomCreateDto(@JsonProperty("title") String title,
                                 @JsonProperty("notice") String notice,
                                 @JsonProperty("category_id") Long categoryId,
                                 @JsonProperty("maximum_capacity") int maximumCapacity,
                                 @JsonProperty("password") String password) {
        this.title = title;
        this.notice = notice;
        this.categoryId = categoryId;
        this.maximumCapacity = maximumCapacity;
        this.password = password;
    }
}
