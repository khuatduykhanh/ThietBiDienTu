package com.example.thietbidientu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bill")
public class Bill {
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
    @JoinColumn(name = "user_id") // đây là khoá ngoại của Post
    private User user;
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private Set<DetailBill> bills = new HashSet<>();

}
