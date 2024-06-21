package com.api.zoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.zoo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByRootCommentIsNullAndMessageContainingIgnoreCase(String message);

    List<Comment> findByRootCommentIsNotNullAndMessageContainingIgnoreCase(String message);
}
