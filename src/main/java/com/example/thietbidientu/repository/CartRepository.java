package com.example.thietbidientu.repository;

import com.example.thietbidientu.entity.Cart;
import com.example.thietbidientu.entity.DetailBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository  extends JpaRepository<Cart,Long> {

    @Query(value = "SELECT * FROM cart p WHERE p.user_id = :userId", nativeQuery = true)
    Optional<Cart> findByUserId(@Param("userId") Long userId);
}
