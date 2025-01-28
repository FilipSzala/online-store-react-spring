package com.example.fullstack_backend.model.product;

import com.example.fullstack_backend.model.category.Category;
import com.example.fullstack_backend.model.image.Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor



public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name="category_id",
            referencedColumnName = "id",
    foreignKey = @ForeignKey(name="product_category_id"))
    private Category category;
    @OneToMany(mappedBy="product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> images;

}
