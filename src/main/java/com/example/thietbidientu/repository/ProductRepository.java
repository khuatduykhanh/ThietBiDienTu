package com.example.thietbidientu.repository;

import com.example.thietbidientu.entity.Product;
import com.example.thietbidientu.entity.RefreshToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAllByCategory(Pageable pageable,String category);
    Page<Product> findAllByBrand(Pageable pageable,String brand);

}
