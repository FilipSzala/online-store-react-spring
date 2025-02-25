package com.example.fullstack_backend.model.order_item;

import com.example.fullstack_backend.model.order.Order;
import com.example.fullstack_backend.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class OrderItemService implements IOrderItemService{
    @Override
    public OrderItem createOrderItem(int quantity, BigDecimal unitPrice, Order order, Product product) {
        if (quantity > 0 && (unitPrice.compareTo(BigDecimal.ZERO)>0) && order != null && product != null) {
            return new OrderItem(quantity, unitPrice, order, product);
        } else {
            throw new IllegalArgumentException("It's impossible to create order because some value are empty");
        }
    }}
