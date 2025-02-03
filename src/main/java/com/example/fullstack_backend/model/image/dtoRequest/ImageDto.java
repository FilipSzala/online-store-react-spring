package com.example.fullstack_backend.model.image.dtoRequest;

import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private String fileName;
    private String downloadUrl;
}
