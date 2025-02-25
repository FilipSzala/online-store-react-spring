package com.example.fullstack_backend.model.cart;

import com.example.fullstack_backend.model.cart_item.CartItemRepository;
import com.example.fullstack_backend.model.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    public Cart getCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new EntityNotFoundException("Cart not found!"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(()->new Cart());
    }

    @Override
    @Transactional
    public void deleteCart(Long cartId) {
        Cart cart = getCart(cartId);
        cartItemRepository.deleteAllByCartId(cartId);
        cartRepository.deleteById(cartId);
    }

    @Override
    public Cart initializeNewCartForUser(User user) {
        return Optional.ofNullable(user.getCart()).orElseGet(()->{
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalAmount();
    }
}
