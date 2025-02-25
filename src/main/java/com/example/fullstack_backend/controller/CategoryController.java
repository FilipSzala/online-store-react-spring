package com.example.fullstack_backend.controller;

import com.example.fullstack_backend.model.category.Category;
import com.example.fullstack_backend.model.category.ICategoryService;
import com.example.fullstack_backend.model.product.dtoRespone.ApiResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")

public class CategoryController {
    private final ICategoryService categoryService;


    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllCategories() {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found", categories));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
            Category savedCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Success", savedCategory));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
            Category category = categoryService.findCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", category));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory (@PathVariable Long id){
            categoryService.deleteCategoryById(id);
            return ResponseEntity.noContent().build();
    }

    @PutMapping ("/{id}")
    public ResponseEntity<ApiResponse> updateCategory (@PathVariable Long id, @RequestBody Category category){
            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Update success!", updatedCategory));
    }

    @GetMapping("/{name}/")
    public ResponseEntity<ApiResponse> getCategoryByName (@PathVariable String name){
            Category category = categoryService.findCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found", category));
    }


}