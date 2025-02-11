package com.example.fullstack_backend.model.order.dtoResponse;

import com.example.fullstack_backend.model.order.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrderDto {
    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
}
