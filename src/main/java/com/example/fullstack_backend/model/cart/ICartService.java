package com.example.fullstack_backend.model.cart;

import com.example.fullstack_backend.model.user.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long cartId);
    Cart getCartByUserId(Long userId);
    void deleteCart(Long cartId);
    Cart initializeNewCartForUser(User user);
    BigDecimal getTotalPrice(Long cartId);
}
