package com.example.fullstack_backend.model.product;

import com.example.fullstack_backend.model.product.dtoRequest.AddProductRequest;
import com.example.fullstack_backend.model.product.dtoRequest.UpdateProductRequest;
import com.example.fullstack_backend.model.product.dtoRespone.ProductDto;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product addProduct(AddProductRequest product);

    Product updateProduct(UpdateProductRequest product, Long productId);

    Product getProductById(Long productId);

    void deleteProductById(Long productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByBrandAndName(String brand, String name);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrand(String brand);

    List<ProductDto> getCovertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
