package com.example.thietbidientu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users",uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fullname",nullable = false)
    private String fullname;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "yourphone", nullable = false)
    private String yourphone;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Loại lấy dữ liệu này có nghĩa là dữ liệu liên quan sẽ được tải ngay lập tức, hoặc ngay cùng với thực thể chính.
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"), // đánh dấu id là khoá ngoại của bảng User và có tên là user_roles
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") // đánh dấu id của bảng role là khoá ngoại và có tên là role_id
    )
    private Set<Role> roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Bill> bills = new HashSet<>();
    @OneToOne(mappedBy = "user1", cascade = CascadeType.ALL)
    private Cart cart;

}
