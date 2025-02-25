package com.example.fullstack_backend.model.cart_item;

import com.example.fullstack_backend.model.cart.Cart;
import com.example.fullstack_backend.model.cart.CartRepository;
import com.example.fullstack_backend.model.cart.ICartService;
import com.example.fullstack_backend.model.product.IProductService;
import com.example.fullstack_backend.model.product.Product;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final ICartService cartService;
    private final IProductService productService;
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public void addCartItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItemToSave;

        Optional<CartItem> optionalCartItem = cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst();

        if (optionalCartItem.isPresent()) {
            CartItem existingCartItem = optionalCartItem.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemToSave = existingCartItem;

        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setUnitPrice(product.getPrice());
            cartItemToSave = newCartItem;
        }
        cartItemToSave.setTotalPrice();
        cart.addItem(cartItemToSave);
        cartItemRepository.save(cartItemToSave);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeCartItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItemToRemove = getCartItemByCartIdAndProductId(cartId, productId);
        cart.removeCartItem(cartItemToRemove);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void updateCartItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);

        cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().ifPresentOrElse(item -> {
            item.setQuantity(quantity);
            item.setUnitPrice(item.getProduct().getPrice());
            item.setTotalPrice();
        }, ()-> {
            throw new EntityNotFoundException("cart item doesn't exist");
        });

        BigDecimal totalAmount = CalculateTotalAmount(cart);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);

    }

    private BigDecimal CalculateTotalAmount(Cart cart) {
        BigDecimal totalAmount = cart.getCartItems().stream()
                .map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalAmount;
    }

    @Override
    public CartItem getCartItemByCartIdAndProductId(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new EntityNotFoundException("CartItem not found!"));
    }
}
