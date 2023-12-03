package com.example.thietbidientu.repository;

import com.example.thietbidientu.entity.DetailCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailCartRepository extends JpaRepository<DetailCart,Long> {
    List<DetailCart> findAllByCartId(Long cartId);

}
