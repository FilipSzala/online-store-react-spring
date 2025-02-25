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
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error :", e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        try {
            Category savedCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Success", savedCategory));
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Error :", e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try {
            Category category = categoryService.findCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", category));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error :", e.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory (@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted", null));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping ("/{id}")
    public ResponseEntity<ApiResponse> updateCategory (@PathVariable Long id, @RequestBody Category category){
        try {
            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Update success!", updatedCategory));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{name}/")
    public ResponseEntity<ApiResponse> getCategoryByName (@PathVariable String name){
        try {
            Category category = categoryService.findCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found", category));
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error :", e.getMessage()));
        }

    }


}