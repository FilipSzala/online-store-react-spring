package com.example.fullstack_backend.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class LoginRequest {

    @NotBlank(message = "Invalid login credentials")
    private String email;
    private String password;
}
