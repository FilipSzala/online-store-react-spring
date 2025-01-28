package com.example.fullstack_backend.model.role;

import com.example.fullstack_backend.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany (mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public Role (String name){
        this.name = name;
    }

}
