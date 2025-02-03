package com.example.fullstack_backend.model.cart;

import com.example.fullstack_backend.model.cart_item.CartItem;
import com.example.fullstack_backend.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Set<CartItem> items = new HashSet<>();

    public Cart() {
    }

    public void removeItem(CartItem cartItem) {
       this.items.remove(cartItem);
       cartItem.setCart(null);
       updateTotalAmount();
    }

    private void updateTotalAmount() {
    }
}
