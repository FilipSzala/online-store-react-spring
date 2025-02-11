package com.example.fullstack_backend.model.order;

import com.example.fullstack_backend.model.order.dtoResponse.OrderDto;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    List<Order> getUserOrders (Long userId);


    OrderDto convertToDto(Order order);
}
