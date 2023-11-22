package com.example.thietbidientu.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DetailBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "price",nullable = false)
    private int price;
    @Column(name = "quantity",nullable = false)
    private int quantity;
    @OneToOne()
    @JoinColumn(name = "productId")
    private Product product2;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId")
    private Bill bill;
}
