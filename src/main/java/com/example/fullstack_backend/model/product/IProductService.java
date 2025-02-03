package com.example.fullstack_backend.model.product;

import com.example.fullstack_backend.model.product.dtoRequest.AddProductRequest;
import com.example.fullstack_backend.model.product.dtoRequest.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product updateProduct(UpdateProductRequest product, Long productId);
    Product getProductById (Long productId);
    void deleteProductById(Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByBrandAndName(String brand,String name);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrand(String brand);


}
