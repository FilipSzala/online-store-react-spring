package com.example.fullstack_backend.model.user.dtoUserResponse;

import com.example.fullstack_backend.model.cart.dtoResponse.CartDto;
import com.example.fullstack_backend.model.order.dtoResponse.OrderDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
