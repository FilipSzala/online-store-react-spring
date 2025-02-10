package com.example.fullstack_backend.model.cart_item;

import com.example.fullstack_backend.model.cart.Cart;
import com.example.fullstack_backend.model.cart.CartRepository;
import com.example.fullstack_backend.model.cart.ICartService;
import com.example.fullstack_backend.model.product.IProductService;
import com.example.fullstack_backend.model.product.Product;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {
    private final ItemRepository itemRepository;
    private final ICartService cartService;
    private final IProductService productService;
    private final CartRepository cartRepository;
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        Item item = cart.getItems()
                .stream()
                .filter(cartItem ->cartItem.getProduct().getId().equals(productId))
                .findFirst().orElse(new Item());
        if (item.getId()==null){
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setUnitPrice(product.getPrice());
        }
        else {
            item.setQuantity(item.getQuantity() + quantity);
        }
        item.setTotalPrice();
        cart.addItem(item);
        itemRepository.save(item);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        Item itemToRemove = getItemByCartIdAndProductId(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

    }

    @Override
    public Item getItemByCartIdAndProductId(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()->new EntityNotFoundException("Cart not found!"));
    }
}
