package com.example.fullstack_backend.model.user;
import com.example.fullstack_backend.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Product, Long> {
}
