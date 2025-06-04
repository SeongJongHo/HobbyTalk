package com.jongho.category.infra.mapper;

import com.jongho.category.domain.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper {
    List<Category> selectMainCategory();
    List<Category> selectSubCategory(Long parentId);
    Category selectOneCategoryById(Long categoryId);
    @Select("SELECT id FROM hobby_categories")
    List<Long> selectCategoryIds();
}
