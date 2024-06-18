package com.api.zoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.zoo.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByValue(String value);

    List<Token> findAllByUserId(Long userId);

    void deleteByValueAndUserId(String value, Long userId);

    Boolean existsByValueAndUserId(String value, Long userId);
}