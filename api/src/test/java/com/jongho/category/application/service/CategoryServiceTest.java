package com.jongho.category.application.service;

import com.jongho.category.domain.model.Category;
import com.jongho.category.application.repository.ICategoryRepository;
import com.jongho.common.exception.CategoryNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryServiceImpl 클래스")
public class CategoryServiceTest {
    @Mock
    private ICategoryRepository ICategoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @Nested
    @DisplayName("getMainCategory 메소드는")
    class Describe_getMainCategory {

        @Test
        @DisplayName("CategoryRepository.selectMainCategory()을 한번 호출하고 반환받은 값을 반환한다.")
        void it_calls_CategoryRepository_selectMainCategory() {
            // given
            List<Category> expectCategories = List.of(new Category(1L, "main", 2L));
            when(ICategoryRepository.selectMainCategory()).thenReturn(expectCategories);

            // when
            categoryService.getMainCategory();

            // then
            verify(ICategoryRepository, times(1)).selectMainCategory();
            assertEquals(expectCategories, categoryService.getMainCategory());
        }
    }

    @Nested
    @DisplayName("getSubCategory 메소드는")
    class Describe_getSubCategory {
        @Test
        @DisplayName("CategoryRepository.selectOneCategoryById()을 한번 호출하고 값이 없다면 CategoryNotFoundException을 반환한다.")
        void CategoryRepository_selectOneCategoryById를_한번_호출하고_해당하는_데이터가_없으면_CategoryNotFoundException을_반환한다() {
            // given
            Long parentId = 1L;
            when(ICategoryRepository.selectOneCategoryById(parentId)).thenReturn(Optional.empty());

            // when then
            CategoryNotFoundException e = assertThrows(CategoryNotFoundException.class, () -> categoryService.getSubCategory(parentId));
            assertEquals("존재하지 않는 카테고리입니다.", e.getMessage());
        }

        @Test
        @DisplayName("CategoryRepository.selectSubCategory()을 한번 호출하고 반환받은 값을 반환한다.")
        void CategoryRepository_selectSubCategory를_한번_호출하고_반환받은_값을_반환한다() {
            // given
            Long parentId = 1L;
            List<Category> expectCategories = List.of(new Category(1L, "main", 2L));
            when(ICategoryRepository.selectOneCategoryById(parentId)).thenReturn(Optional.of(new Category(1L, "main", 2L)));
            when(ICategoryRepository.selectSubCategory(parentId)).thenReturn(expectCategories);

            // when
            categoryService.getSubCategory(parentId);

            // then
            verify(ICategoryRepository, times(1)).selectSubCategory(parentId);
            assertEquals(expectCategories, categoryService.getSubCategory(parentId));
        }
    }
}
