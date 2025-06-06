package com.jongho.category.application.repository;

import com.jongho.category.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository {
    List<Category> selectMainCategory();
    List<Category> selectSubCategory(Long parentId);
    Category selectOneCategoryById(Long categoryId);
    List<Long> selectCategoryIds();
}
