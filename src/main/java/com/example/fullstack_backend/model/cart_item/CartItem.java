package com.example.fullstack_backend.model.cart_item;

import com.example.fullstack_backend.model.cart.Cart;
import com.example.fullstack_backend.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "product_id",
                referencedColumnName = "",
                foreignKey = @ForeignKey(
                        name = "product_id_fk"))
    private Product product;
    @ManyToOne (
            cascade = CascadeType.ALL
    )
    @JoinColumn(name= "cart_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(
                        name = "cart_id_fk"
                ))
    private Cart cart;

    public void setTotalPrice(){
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));
    }

}
