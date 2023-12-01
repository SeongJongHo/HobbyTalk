package com.jongho.category.controller;

import com.jongho.annotaition.HttpRequestLogging;
import com.jongho.category.application.service.CategoryService;

import com.jongho.category.domain.model.Category;
import com.jongho.response.BaseResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@HttpRequestLogging
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/main")
    public ResponseEntity<?> getMainCategory(){

        return BaseResponseEntity.ok(categoryService.getMainCategory(), "success");
    }

    @GetMapping("/{parentId}/sub")
    public ResponseEntity<?> getSubCategory(@PathVariable Long parentId){

        return BaseResponseEntity.ok(categoryService.getSubCategory(parentId), "success");
    }
}