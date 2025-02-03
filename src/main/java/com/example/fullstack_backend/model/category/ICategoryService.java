package com.example.fullstack_backend.model.category;

import java.util.List;

public interface ICategoryService {
    Category addCategory(Category category);
    Category updateCategory (Category category,Long categoryId);
    void deleteCategoryId(Long categoryId);
    List<Category> getAllCategories();
    Category findCategoryByName(String name);
    Category findCategoryById(Long categoryId);
}
