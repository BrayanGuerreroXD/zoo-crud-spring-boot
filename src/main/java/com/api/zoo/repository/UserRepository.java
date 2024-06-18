package com.api.zoo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.zoo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    
    Optional<User> findByEmail(String email);
}