package com.example.thietbidientu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
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
    @OneToOne(mappedBy = "product2",cascade = CascadeType.ALL)
    private DetailBill detailBill;
}
