package com.example.thietbidientu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "total",nullable = false)
    private int total;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId") // đây là khoá ngoại của Post
    private User user1;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<DetailCart> carts = new HashSet<>();

}
