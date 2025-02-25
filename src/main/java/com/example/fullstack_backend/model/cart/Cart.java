package com.example.fullstack_backend.model.cart;

import com.example.fullstack_backend.model.cart_item.CartItem;
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
    private Set<CartItem> cartItems = new HashSet<>();

    public Cart() {
    }

    public void removeCartItem(CartItem cartItem) {
       this.cartItems.remove(cartItem);
       cartItem.setCart(null);
       updateTotalAmount();
    }

    private void updateTotalAmount() {
        this.totalAmount = cartItems.stream().map(item -> item.getTotalPrice()).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public void addItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        updateTotalAmount();
    }

    public void clearCartItems() {
        this.cartItems.clear();
    }

    public boolean isEmpty() {
        return (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) == 0)
                && (cartItems == null || cartItems.isEmpty())
                && user == null;
    }


}
