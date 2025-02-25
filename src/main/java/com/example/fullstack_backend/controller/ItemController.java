package com.example.fullstack_backend.controller;

import com.example.fullstack_backend.model.cart.Cart;
import com.example.fullstack_backend.model.cart.ICartService;
import com.example.fullstack_backend.model.cart_item.ICartItemService;
import com.example.fullstack_backend.model.product.dtoRespone.ApiResponse;
import com.example.fullstack_backend.model.user.IUserService;
import com.example.fullstack_backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart-items")
public class ItemController {
    private final ICartItemService itemService;
    private final IUserService userService;
    private final ICartService cartService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> addCartItemToCart(@RequestParam Long productId, @RequestParam int quantity){
        User user = userService.getAuthenticatedUser();
        Cart cart = cartService.initializeNewCartForUser(user);
        itemService.addCartItemToCart(cart.getId(),productId,quantity);
        return ResponseEntity.ok(new ApiResponse("CartItem added successfully!", null));
    }

    @DeleteMapping("/{cart-itemId}/cart/{cartId}")
    public ResponseEntity<ApiResponse> deleteCartItemFromCart (@PathVariable Long cartId, @PathVariable Long cartItemId){
        itemService.removeCartItemFromCart(cartId, cartItemId);
        return ResponseEntity.ok(new ApiResponse("CartItem removed successfully!",null));
    }

    @PutMapping("/{cart-itemId}/cart/{cartId}")
    public ResponseEntity<ApiResponse> updateCartItemInCart (@PathVariable Long cartId,@PathVariable Long cartItemId, @RequestParam int quantity){
        itemService.updateCartItemQuantity(cartId, cartItemId, quantity);
        return ResponseEntity.ok(new ApiResponse("CartItem updated successfully", null));
    }
}
