package com.api.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.zoo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}