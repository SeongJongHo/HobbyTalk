package com.jongho.category.infra.repository;

import com.jongho.category.application.repository.ICategoryRepository;
import com.jongho.category.domain.model.Category;
import com.jongho.category.infra.mapper.CategoryMapper;
import com.jongho.common.cache.CustomCacheType;
import com.jongho.common.cache.CustomCacheable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CategoryRepository implements ICategoryRepository {
    private final CategoryMapper categoryMapper;

    @CustomCacheable(
        prefix = "categories",
        type = CustomCacheType.Type.LIST
    )
    public List<Category> selectMainCategory() {
        return categoryMapper.selectMainCategory();
    }

    @Override
    public List<Category> selectSubCategory(Long parentId) {

        return categoryMapper.selectSubCategory(parentId);
    }

    @CustomCacheable(
        prefix = "category",
        keys = {"categoryId"},
        ttlSeconds = 30
    )
    @Override
    public Category selectOneCategoryById(Long categoryId) {

        return categoryMapper.selectOneCategoryById(categoryId);
    }

    @CustomCacheable(
        prefix = "categoryIds",
        type = CustomCacheType.Type.LIST
    )
    @Override
    public List<Long> selectCategoryIds() {
        return categoryMapper.selectCategoryIds();
    }
}
