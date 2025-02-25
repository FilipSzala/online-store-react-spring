package com.example.fullstack_backend.model.order_item;

import com.example.fullstack_backend.model.order.Order;
import com.example.fullstack_backend.model.product.Product;

import java.math.BigDecimal;

public interface IOrderItemService {
     OrderItem createOrderItem (int quantity, BigDecimal unitPrice, Order order, Product product) throws Exception;
}
