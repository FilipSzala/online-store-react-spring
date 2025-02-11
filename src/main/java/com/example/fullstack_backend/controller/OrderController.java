package com.example.fullstack_backend.controller;

import com.example.fullstack_backend.model.order.IOrderService;
import com.example.fullstack_backend.model.order.Order;
import com.example.fullstack_backend.model.order.dtoResponse.OrderDto;
import com.example.fullstack_backend.model.product.dtoRespone.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/user")
    public ResponseEntity <ApiResponse> placeOrder (@RequestParam Long userId){
        Order order = orderService.placeOrder(userId);
        OrderDto orderDto = orderService.convertToDto(order);
        return ResponseEntity.ok(new ApiResponse("Order placed successfully!", orderDto));
    }

    @GetMapping("/user/{userId}")
    private ResponseEntity<ApiResponse> getUserOrders (@PathVariable  Long userId){
        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse("Success!", orders));
    }
}

