package com.example.fullstack_backend.model.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        return null;
    }

    @Override
    public void deleteProductById(Long productId) {

    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException("Product not found"));
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        return productRepository.findByCategory_Name(categoryName);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String categoryName, String brand) {
        return productRepository.findByCategory_NameAndBrand(categoryName,brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }
}
