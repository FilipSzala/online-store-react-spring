package com.example.fullstack_backend.model.cart_item;

public interface ICartItemService {
    void addCartItemToCart(Long cartId, Long productId, int quantity);
    void removeCartItemFromCart(Long cartId, Long itemId);
    void updateCartItemQuantity(Long cartId, Long productId, int quantity);
    CartItem getCartItemByCartIdAndProductId(Long cartId, Long productId);

}
