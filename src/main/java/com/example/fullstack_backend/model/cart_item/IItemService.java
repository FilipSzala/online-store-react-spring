package com.example.fullstack_backend.model.cart_item;

public interface IItemService {
    void addItemToCart (Long cartId, Long productId, int quantity);
    void removeItemFromCart (Long cartId, Long productId);
    void updateItemQuantity(Long cartId,Long productId, int quantity);
    Item getItemByCartIdAndProductId(Long cartId, Long productId);

}
