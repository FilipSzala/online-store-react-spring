package com.example.fullstack_backend.model.cart_item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByProductId(Long productId);
}