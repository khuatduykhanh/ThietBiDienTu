package com.example.thietbidientu.repository;

import com.example.thietbidientu.entity.DetailBill;
import com.example.thietbidientu.entity.DetailInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailInvoiceRepository extends JpaRepository<DetailInvoice,Long> {
    @Query(value = "SELECT * FROM detail_invoice p WHERE p.invoice_id = :invoiceId", nativeQuery = true)
    List<DetailInvoice> findAllByInvoiceId(@Param("invoiceId")Long invoiceId);
}
