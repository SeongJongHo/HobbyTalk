package com.jongho.category.application.service;

import com.jongho.category.domain.model.Category;
import com.jongho.category.application.repository.CategoryRepository;
import com.jongho.common.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl {
    private final CategoryRepository categoryRepository;
    public List<Category> getMainCategory() {

        return categoryRepository.selectMainCategory();
    }

    public List<Category> getSubCategory(Long parentId) {
        Optional<Category> mainCategory = categoryRepository.selectOneCategoryById(parentId);

        if(mainCategory.isEmpty()){
            throw new CategoryNotFoundException("존재하지 않는 카테고리입니다.");
        }

        return categoryRepository.selectSubCategory(parentId);
    }

    public Optional<Category> getOneCategoryById(Long categoryId) {
        return categoryRepository.selectOneCategoryById(categoryId);
    }
}
