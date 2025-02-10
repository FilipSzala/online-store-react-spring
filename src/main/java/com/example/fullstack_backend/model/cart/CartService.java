package com.example.fullstack_backend.model.cart;

import com.example.fullstack_backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service

public class CartService implements ICartService {
    private final CartRepository cartRepository;
    @Override
    public Cart getCart(Long cartId) {
        return null;
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return null;
    }

    @Override
    public void clearCart(Long cartId) {

    }

    @Override
    public Cart initializeNewCartForUser(User user) {
        return null;
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        return null;
    }
}
