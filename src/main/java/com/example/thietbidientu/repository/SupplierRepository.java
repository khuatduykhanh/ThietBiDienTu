package com.example.thietbidientu.repository;

import com.example.thietbidientu.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long>{
    Optional<Supplier> findByName(String name);
}
