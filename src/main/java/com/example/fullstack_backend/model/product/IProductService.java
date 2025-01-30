package com.example.fullstack_backend.model.product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);
    Product updateProduct(Product product, Long productId);
    Product getProductById (Long productId);
    void deleteProductById(Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByBrandAndName(String brand,String name);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrand(String brand);


}
