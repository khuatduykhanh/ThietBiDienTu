package com.example.thietbidientu.repository;

import com.example.thietbidientu.dto.DetailDto;
import com.example.thietbidientu.entity.Bill;
import com.example.thietbidientu.entity.DetailBill;
import com.example.thietbidientu.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailBillRepository extends JpaRepository<DetailBill,Long> {
//    Page<DetailBill>  findAllByBillId(Pageable pageable, Long billId);
        @Query(value = "SELECT * FROM detail_bill p WHERE p.bill_id = :billId", nativeQuery = true)
        List<DetailBill> findAllByBillId(@Param("billId")Long billId);
}
