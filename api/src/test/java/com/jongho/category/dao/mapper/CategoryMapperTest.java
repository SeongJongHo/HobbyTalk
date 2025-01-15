package com.jongho.category.dao.mapper;

import com.jongho.category.domain.model.Category;
import com.jongho.category.infra.dao.mapper.CategoryMapper;
import com.jongho.common.dao.BaseMapperTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("CategoryMapper 인터페이스")
public class CategoryMapperTest extends BaseMapperTest {
    @Autowired
    private CategoryMapper categoryMapper;

    @Nested
    @DisplayName("selectMainCategory 메소드는")
    class Describe_selectMainCategory {
        @BeforeEach
        void setUp() {
            setUpCategoryTable(); // main category 5개, sub category 5개 총 10개의 카테고리를 생성한다.
        }
        @AfterEach
        void tearDown() {
            cleanUpCategoryTable();
        }
        @Test
        @DisplayName("메인 카테고리를 조회한다.")
        void 메인_카테고리를_조회한다() {
            // when
            List<Category> categories = categoryMapper.selectMainCategory();

            // then
            assertEquals(5, categories.size());
        }
    }

    @Nested
    @DisplayName("selectSubCategory 메소드는")
    class Describe_selectSubCategory {
        @BeforeEach
        void setUp() {
            setUpCategoryTable(); // main category 5개, sub category 5개 총 10개의 카테고리를 생성한다.
        }
        @AfterEach
        void tearDown() {
            cleanUpCategoryTable();
        }
        @Test
        @DisplayName("서브 카테고리를 조회한다.")
        void 서브_카테고리를_조회한다() {
            // given
            Long categoryId = 1L;

            // when
            List<Category> categories = categoryMapper.selectSubCategory(categoryId);

            // then
            for (Category category : categories) {
                assertEquals(categoryId, category.getParentId());
            }
            assertEquals(1, categories.size());
        }
    }

    @Nested
    @DisplayName("selectOneCategoryById 메소드는")
    class Describe_selectOneCategoryById {
        @BeforeEach
        void setUp() {
            setUpCategoryTable(); // main category 5개, sub category 5개 총 10개의 카테고리를 생성한다.
        }
        @AfterEach
        void tearDown() {
            cleanUpCategoryTable();
        }
        @Test
        @DisplayName("전달받은 id로 해당 카테고리를 조회한다.")
        void 전달받은_id로_해당_카테고리를_조회한다() {
            // given
            Long categoryId = 1L;

            // when
            Category category = categoryMapper.selectOneCategoryById(categoryId);

            // then
            assertEquals(categoryId, category.getId());
        }
    }
}
