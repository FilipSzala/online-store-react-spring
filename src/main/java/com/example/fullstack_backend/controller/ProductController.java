package com.example.fullstack_backend.controller;


import com.example.fullstack_backend.model.product.IProductService;
import com.example.fullstack_backend.model.product.Product;
import com.example.fullstack_backend.model.product.ProductRepository;
import com.example.fullstack_backend.model.product.dtoRequest.AddProductRequest;
import com.example.fullstack_backend.model.product.dtoRequest.UpdateProductRequest;
import com.example.fullstack_backend.model.product.dtoRespone.ApiResponse;
import com.example.fullstack_backend.model.product.dtoRespone.ProductDto;
import lombok.RequiredArgsConstructor;
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
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Found :", productDto));
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String name, @RequestParam String brand) {
            List<Product> product = productService.getProductsByBrandAndName(brand, name);
            List<ProductDto> productDtos = productService.getCovertedProducts(product);
            return ResponseEntity.ok(new ApiResponse("Products :", productDtos));
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryNameAndBrand(@RequestParam String categoryName, @RequestParam String brand) {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brand);
            List<ProductDto> productDtos = productService.getCovertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Products :", productDtos));
    }

    @GetMapping("/by/name")
    public ResponseEntity<ApiResponse> getProductByName (@RequestParam String name){
        List<Product> products = productService.getProductsByName(name);
        List<ProductDto> productDtos = productService.getCovertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Produtcs :",productDtos));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest productRequest) {
            Product product = productService.addProduct(productRequest);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Product added :", productDto));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest updateProductRequest, @PathVariable Long productId) {
            Product product = productService.updateProduct(updateProductRequest, productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Product updated :", productDto));
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product with id : " + productId +" deleted", null));
    }
}


