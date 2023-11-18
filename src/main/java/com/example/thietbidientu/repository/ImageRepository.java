package com.example.thietbidientu.repository;

import com.example.thietbidientu.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ImageRepository extends JpaRepository<ImageData,Long> {

    Optional<ImageData> findByName(String fileName);
    Optional<ImageData> findAllByProductId(Long productId);
}
