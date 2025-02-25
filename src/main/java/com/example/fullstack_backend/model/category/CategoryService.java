package com.example.fullstack_backend.model.category;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new EntityExistsException(category.getName() + " already exist"));
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Category oldCategory = findCategoryById(categoryId);
        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
        return oldCategory;
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        categoryRepository.findById(categoryId).ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new EntityNotFoundException("Category not found");
                }
        );
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Category not found!"));
    }

    @Override
    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).
                orElseThrow(() -> new EntityNotFoundException("Category not found!"));
    }

    public Category getOrCreateCategory(String name) {
        Category category = categoryRepository.findByName(name).orElseGet(
                () -> {
                    Category newCategory = new Category(name);
                    return categoryRepository.save(newCategory);
                });
        return category;
    }
}
