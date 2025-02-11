package com.example.fullstack_backend.controller;

import com.example.fullstack_backend.model.cart.Cart;
import com.example.fullstack_backend.model.cart.ICartService;
import com.example.fullstack_backend.model.cart_item.IItemService;
import com.example.fullstack_backend.model.product.dtoRespone.ApiResponse;
import com.example.fullstack_backend.model.user.IUserService;
import com.example.fullstack_backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/items")
public class ItemController {
    private final IItemService itemService;
    private final IUserService userService;
    private final ICartService cartService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> addItemToCart (/*Long userId*/ @RequestParam Long productId, @RequestParam int quantity){
        User user = userService.getUserById(1L);
        Cart cart = cartService.initializeNewCartForUser(user);
        itemService.addItemToCart(cart.getId(),productId,quantity);
        return ResponseEntity.ok(new ApiResponse("Item added successfully!", null));
    }

    @DeleteMapping("/{itemId}/cart/{cartId}")
    public ResponseEntity<ApiResponse> deleteItemFromCart (@PathVariable Long cartId, @PathVariable Long itemId){
        itemService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok(new ApiResponse("Item removed successfully!",null));
    }

    @PutMapping("/{itemId}/cart/{cartId}")
    public ResponseEntity<ApiResponse> updateItemInCart (@PathVariable Long cartId,@PathVariable Long itemId, @RequestParam int quantity){
        itemService.updateItemQuantity(cartId, itemId, quantity);
        return ResponseEntity.ok(new ApiResponse("Item updated successfully", null));
    }
}
