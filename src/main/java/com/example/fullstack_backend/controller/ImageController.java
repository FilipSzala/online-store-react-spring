package com.example.fullstack_backend.controller;


import com.example.fullstack_backend.model.image.IImageService;
import com.example.fullstack_backend.model.image.dtoResponse.ImageDto;
import com.example.fullstack_backend.model.product.dtoRespone.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> saveImages (@RequestParam Long productId, MultipartFile files) {
        try {
            List<ImageDto> imageDto = imageService.saveImages(productId, files);
            return ResponseEntity.ok(new ApiResponse("Images uploaded successfully!", imageDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload error!", e.getMessage()));
        }
    }
}