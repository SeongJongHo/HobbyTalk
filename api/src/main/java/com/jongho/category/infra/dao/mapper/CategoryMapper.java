package com.jongho.category.infra.dao.mapper;

import com.jongho.category.domain.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectMainCategory();
    List<Category> selectSubCategory(Long parentId);
    Category selectOneCategoryById(Long categoryId);
}
