package com.example.fullstack_backend.model.cart;

import com.example.fullstack_backend.model.cart_item.Item;
import com.example.fullstack_backend.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Cart {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount;
    @OneToOne ()
    @JoinColumn(name="user_id",
                referencedColumnName = "id")
    private User user;
    @OneToMany (
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Item> items = new HashSet<>();

    public Cart() {
    }

    public void removeItem(Item item) {
       this.items.remove(item);
       item.setCart(null);
       updateTotalAmount();
    }

    private void updateTotalAmount() {
        this.totalAmount = items.stream().map(item -> item.getTotalPrice()).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public void addItem(Item item) {
        this.items.add(item);
        updateTotalAmount();
    }

    public void clearItems() {
        this.items.clear();
        updateTotalAmount();
    }
}
