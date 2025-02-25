package com.example.fullstack_backend.model.product.dtoRequest;

import com.example.fullstack_backend.model.category.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;


    public static boolean hasEmptyFields(AddProductRequest request) {
        return request.getName() == null || request.getName().trim().isEmpty() ||
                request.getBrand() == null || request.getBrand().trim().isEmpty() ||
                request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0 ||
                request.getInventory() < 0 ||
                request.getDescription() == null || request.getDescription().trim().isEmpty() ||
                request.getCategory() == null;
    }
}
