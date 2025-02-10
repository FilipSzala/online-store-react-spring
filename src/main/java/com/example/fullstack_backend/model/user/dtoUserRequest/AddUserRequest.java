package com.example.fullstack_backend.model.user.dtoUserRequest;

import lombok.Data;

@Data
public class AddUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
