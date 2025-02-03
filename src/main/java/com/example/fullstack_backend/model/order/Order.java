package com.example.fullstack_backend.model.order;

import com.example.fullstack_backend.model.order_item.OrderItem;
import com.example.fullstack_backend.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    @Enumerated (EnumType.STRING)
    private OrderStatus orderStatus;
    @ManyToOne
    @JoinColumn (name="user_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "user_order_fk"))
    private User user;
    @OneToMany (mappedBy = "order"
            ,cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    public Order() {
    }
}
