package com.example.thietbidientu.repository;

import com.example.thietbidientu.entity.Bill;
import com.example.thietbidientu.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {
    Page<Bill> findAllByUserId(Pageable pageable, Long userId);
}
