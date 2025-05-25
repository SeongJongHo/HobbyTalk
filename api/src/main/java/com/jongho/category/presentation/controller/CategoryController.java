package com.jongho.category.presentation.controller;

import com.jongho.category.application.service.CategoryServiceImpl;
import com.jongho.common.annotaition.HttpRequestLogging;
import com.jongho.common.response.BaseResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category", description = "카테고리 API")
@HttpRequestLogging
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @Operation(summary = "카테고리 조회")
    @GetMapping("/main")
    public ResponseEntity<?> getMainCategory(){

        return BaseResponseEntity.ok(categoryService.getMainCategory(), "success");
    }

    @Operation(summary = "서브 카테고리 조회")
    @GetMapping("/{parentId}/sub")
    public ResponseEntity<?> getSubCategory(@PathVariable Long parentId){

        return BaseResponseEntity.ok(categoryService.getSubCategory(parentId), "success");
    }
}