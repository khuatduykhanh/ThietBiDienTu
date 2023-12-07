package com.example.thietbidientu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "total",nullable = false)
    private int total;
    @Column(name = "dateTime",nullable = false)
    private Date dateTime;
    @Column(name = "status",nullable = false)
    private boolean status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id") // đây là khoá ngoại của Post
    private Supplier supplier;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private Set<DetailInvoice> detailInvoices = new HashSet<>();

}
