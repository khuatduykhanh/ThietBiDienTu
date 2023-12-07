package com.example.thietbidientu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product",uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "category",nullable = false)
    private String category;
    @Column(name = "brand",nullable = false)
    private String brand;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "price",nullable = false)
    private int price;
    @Column(name = "cost",nullable = false)
    private int cost;
    @Column(name = "quantity",nullable = false)
    private int quantity;
    @Column(name = "status",nullable = false)
    private boolean status;
    @OneToOne(mappedBy = "product",cascade = CascadeType.ALL)
    private ImageData image;
    @OneToMany(mappedBy = "product2",cascade = CascadeType.ALL)
    private Set<DetailBill> detailBill = new HashSet<>();
    @OneToMany(mappedBy = "product3",cascade = CascadeType.ALL)
    private Set<DetailCart> detailCart = new HashSet<>() ;
    @OneToMany(mappedBy = "product4",cascade = CascadeType.ALL)
    private Set<DetailInvoice> detailInvoice = new HashSet<>() ;
}
