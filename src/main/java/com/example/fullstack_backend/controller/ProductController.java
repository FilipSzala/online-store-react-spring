package com.example.fullstack_backend.controller;


import com.example.fullstack_backend.model.product.IProductService;
import com.example.fullstack_backend.model.product.Product;
import com.example.fullstack_backend.model.product.ProductRepository;
import com.example.fullstack_backend.model.product.dtoRequest.AddProductRequest;
import com.example.fullstack_backend.model.product.dtoRequest.UpdateProductRequest;
import com.example.fullstack_backend.model.product.dtoRespone.ApiResponse;
import com.example.fullstack_backend.model.product.dtoRespone.ProductDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getCovertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Found :", convertedProducts));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Found :", productDto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Not found :", e.getMessage()));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String name, @RequestParam String brand) {
        try {
            List<Product> product = productService.getProductsByBrandAndName(brand, name);
            List<ProductDto> productDtos = productService.getCovertedProducts(product);
            return ResponseEntity.ok(new ApiResponse("Products :", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryNameAndBrand(@RequestParam String categoryName, @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brand);
            List<ProductDto> productDtos = productService.getCovertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Products :", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/name")
    public ResponseEntity<ApiResponse> getProductByName (@RequestParam String name){
        List<Product> products = productService.getProductsByName(name);
        List<ProductDto> productDtos = productService.getCovertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Produtcs :",productDtos));

    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest productRequest) {
        try {
            Product product = productService.addProduct(productRequest);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Product added :", productDto));
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest updateProductRequest, @PathVariable Long productId) {
        try {
            Product product = productService.updateProduct(updateProductRequest, productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Product updated :", productDto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product with id : " + productId +" deleted", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }


}


