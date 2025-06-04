package com.jongho.category.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Category {
    private final Long id;
    private final String name;
    private final Long parentId;

    @JsonCreator
    public Category(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("parentId") Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
}
