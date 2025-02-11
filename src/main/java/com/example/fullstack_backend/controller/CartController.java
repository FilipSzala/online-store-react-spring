package com.example.fullstack_backend.controller;

import com.example.fullstack_backend.model.cart.Cart;
import com.example.fullstack_backend.model.cart.ICartService;
import com.example.fullstack_backend.model.product.dtoRespone.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserCart(@PathVariable Long userId){
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(new ApiResponse("Success", cart));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity <Void> clearCart(@PathVariable Long cartId){
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }


}
