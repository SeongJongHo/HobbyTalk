package com.jongho.category.presentation.controller;

import com.jongho.category.application.service.CategoryService;
import com.jongho.category.domain.model.Category;
import com.jongho.common.annotaition.HttpRequestLogging;
import com.jongho.common.response.BaseResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category", description = "카테고리 API")
@HttpRequestLogging
@RestController
@RequestMapping("/api/public/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "카테고리 조회")
    @GetMapping("/main")
    public ResponseEntity<?> getMainCategory(){

        return BaseResponseEntity.ok(categoryService.getMainCategory(), "success");
    }

    @Operation(summary = "카테고리 조회")
    @GetMapping("")
    public ResponseEntity<BaseResponseEntity<List<Category>>> getCategories(){

        return BaseResponseEntity.ok(categoryService.getCategories(), "success");
    }

    @Operation(summary = "서브 카테고리 조회")
    @GetMapping("/{parentId}/sub")
    public ResponseEntity<?> getSubCategory(@PathVariable Long parentId){

        return BaseResponseEntity.ok(categoryService.getSubCategory(parentId), "success");
    }
}