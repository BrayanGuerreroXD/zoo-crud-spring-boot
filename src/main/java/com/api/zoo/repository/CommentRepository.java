package com.api.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.zoo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
