package com.jongho.category.presentation;

import com.jongho.category.application.service.CategoryService;
import com.jongho.category.presentation.controller.CategoryController;
import com.jongho.category.domain.model.Category;
import com.jongho.common.config.WebMvcConfig;
import com.jongho.common.interceptor.AuthInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = CategoryController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AuthInterceptor.class, WebMvcConfig.class})
)
@DisplayName("CategoryController 클래스")
public class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("getMainCategory 메소드는")
    class Describe_getMainCategory {
        @Test
        @DisplayName("호출이 되면 status 200과 success라는 메세지를 반환한다.")
        void 호출이_되면_status_200과_success라는_메세지를_반환한다() throws Exception{
            // given
            when(categoryService.getMainCategory()).thenReturn(List.of(new Category(1L, "main", 2L)));

            // when
            mockMvc.perform(get("/api/v1/categories/main")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("success"))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data[0].id").value(1))
                    .andExpect(jsonPath("$.data[0].name").value("main"))
                    .andExpect(jsonPath("$.data[0].parentId").value(2))
                    .andDo(print());

            // then
            verify(categoryService, times(1)).getMainCategory();
        }
    }

    @Nested
    @DisplayName("getSubCategory 메소드는")
    class Describe_getSubCategory {
        @Test
        @DisplayName("호출이 되면 status 200과 success라는 메세지를 반환한다.")
        void 호출이_되면_status_200과_success라는_메세지를_반환한다() throws Exception{
            // given
            List<Category> expectedCategories = List.of(new Category(1L, "main", 2L));
            when(categoryService.getSubCategory(2L)).thenReturn(expectedCategories);

            // when
            mockMvc.perform(get("/api/v1/categories/2/sub")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("success"))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data[0].id").value(1))
                    .andExpect(jsonPath("$.data[0].name").value("main"))
                    .andExpect(jsonPath("$.data[0].parentId").value(2))
                    .andDo(print());

            // then
            verify(categoryService, times(1)).getSubCategory(2L);
        }
    }
}
