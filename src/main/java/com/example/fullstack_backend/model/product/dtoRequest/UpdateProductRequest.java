package com.example.fullstack_backend.model.product.dtoRequest;

import com.example.fullstack_backend.model.category.Category;
import lombok.Data;

import java.math.BigDecimal;
@Data

public class UpdateProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;

    public static void validateUpdateRequest(UpdateProductRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty() ||
                request.getBrand() == null || request.getBrand().trim().isEmpty() ||
                request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0 ||
                request.getInventory() < 0 ||
                request.getDescription() == null || request.getDescription().trim().isEmpty() ||
                request.getCategory() == null || request.getCategory().getName() == null || request.getCategory().getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Request can't have empty fields");
        }
    }
}
