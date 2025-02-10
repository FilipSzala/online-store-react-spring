package com.example.fullstack_backend.model.user.dtoUserResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UserResponseDto {
    private String firstName;
    private String lastName;
    private String email;
}
