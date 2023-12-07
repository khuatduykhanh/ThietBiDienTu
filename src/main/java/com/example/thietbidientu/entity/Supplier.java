package com.example.thietbidientu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "address",nullable = false)
    private String address;
    @Column(name = "contact",nullable = false)
    private String contact;
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<Invoice> invoices = new HashSet<>();
}
