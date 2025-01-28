package com.example.fullstack_backend.model.user;

import com.example.fullstack_backend.model.Cart;
import com.example.fullstack_backend.model.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.query.Order;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    @NaturalId
    private String email;
    private String password;
    @OneToOne (mappedBy = "user",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private Cart cart;
    @OneToMany (
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Order> orders;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable (
            name = "user_role",
            joinColumns = @JoinColumn (
                    name = "user_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(
                            name = "student_role_id_fk"
                    )),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey (
                            name = "role_student_id_fk"
                    )
            ))
    private Collection<Role> roles = new HashSet<>();


}
