package com.jongho.category.infra.dao.repository;

import com.jongho.category.infra.dao.mapper.CategoryMapper;
import com.jongho.category.domain.model.Category;
import com.jongho.category.application.repository.ICategoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryMyBatisRepository implements ICategoryRepository {
    private final CategoryMapper categoryMapper;

    public List<Category> selectMainCategory() {
        return categoryMapper.selectMainCategory();
    }

    public List<Category> selectSubCategory(Long parentId) {

        return categoryMapper.selectSubCategory(parentId);
    }

    public Optional<Category> selectOneCategoryById(Long categoryId) {
        return Optional.ofNullable(categoryMapper.selectOneCategoryById(categoryId));
    }
}
