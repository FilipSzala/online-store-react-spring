package com.example.fullstack_backend.model;

import com.example.fullstack_backend.model.order.Order;
import com.example.fullstack_backend.model.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@Entity

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal price;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "order_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(
                        name = "order_orderitem_fk"
                ))
    private Order order;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "product_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "product_orderitem_fk"
            ))
    private Product product;

    public OrderItem(int quantity, BigDecimal price, Order order, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.product = product;
    }
}
