package com.example.thietbidientu.repository;

import com.example.thietbidientu.entity.Bill;
import com.example.thietbidientu.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    Page<Invoice> findAllBySupplierId(Pageable pageable, Long supplierId);
}
