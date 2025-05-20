package com.jongho.category.application.service;

import com.jongho.category.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getMainCategory();
    List<Category> getSubCategory(Long parentId);
    Optional<Category> getOneCategoryById(Long categoryId);
}
