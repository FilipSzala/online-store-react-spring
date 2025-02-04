package com.example.fullstack_backend.model.product.dtoRespone;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private Object data;
}
