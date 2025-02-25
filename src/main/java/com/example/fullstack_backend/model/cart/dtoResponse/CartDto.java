package com.example.fullstack_backend.model.cart.dtoResponse;

import com.example.fullstack_backend.model.cart_item.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
@Data

public class CartDto {
    private Long id;
    private BigDecimal totalAmount;
    private Set<CartItem> cartItems = new HashSet<>();

}
