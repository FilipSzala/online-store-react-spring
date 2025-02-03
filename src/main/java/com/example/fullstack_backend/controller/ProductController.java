package com.example.fullstack_backend.controller;


import com.example.fullstack_backend.model.product.ProductRepository;
import com.example.fullstack_backend.model.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;



}


