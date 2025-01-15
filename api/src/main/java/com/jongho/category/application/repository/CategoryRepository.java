package com.jongho.category.application.repository;

import com.jongho.category.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    public List<Category> selectMainCategory();
    public List<Category> selectSubCategory(Long parentId);
    public Optional<Category> selectOneCategoryById(Long categoryId);
}
