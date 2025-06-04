package com.jongho.category.application.service;

import com.jongho.category.application.repository.ICategoryRepository;
import com.jongho.category.domain.model.Category;
import com.jongho.common.exception.CategoryNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ICategoryRepository iCategoryRepository;
    public List<Category> getMainCategory() {
        return iCategoryRepository.selectMainCategory();
    }

    public List<Category> getCategories() {
        List<Long> categoryIds = iCategoryRepository.selectCategoryIds();
        List<Category> categories = new ArrayList<>();
        for (Long id : categoryIds) {
            categories.add(iCategoryRepository.selectOneCategoryById(id));
        }

        return categories;
    }

    public List<Category> getSubCategory(Long parentId) {
        Category mainCategory = iCategoryRepository.selectOneCategoryById(parentId);

        if(mainCategory == null) {
            throw new CategoryNotFoundException("존재하지 않는 카테고리입니다.");
        }

        return iCategoryRepository.selectSubCategory(parentId);
    }

    public Category getOneCategoryById(Long categoryId) {
        return iCategoryRepository.selectOneCategoryById(categoryId);
    }
}
