package com.example.fullstack_backend.model.product.dtoRespone;

import com.example.fullstack_backend.model.category.Category;
import com.example.fullstack_backend.model.image.dtoResponse.ImageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDto> images;
}
