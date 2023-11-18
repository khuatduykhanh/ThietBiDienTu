package com.example.thietbidientu.repository;


import com.example.thietbidientu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByFullname(String email);
    boolean existsByEmail(String email);

    @Query(value = "SELECT id FROM users p WHERE p.email = :email", nativeQuery = true)
    int findIdByEmail(@Param("email") String email);
}
