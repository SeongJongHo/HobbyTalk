package com.jongho.category.infra.dao.mapper;

import com.jongho.category.domain.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    public List<Category> selectMainCategory();
    public List<Category> selectSubCategory(Long parentId);
    public Category selectOneCategoryById(Long categoryId);
}
