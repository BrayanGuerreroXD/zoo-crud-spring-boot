package com.api.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.zoo.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    
}