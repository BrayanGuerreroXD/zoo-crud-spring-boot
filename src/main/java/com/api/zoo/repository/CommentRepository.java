package com.api.zoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.zoo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByRootCommentIsNullAndMessageContainingIgnoreCase(String message);

    List<Comment> findByRootCommentIsNotNullAndMessageContainingIgnoreCase(String message);
    
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.id IN (SELECT co.rootComment.id FROM Comment co WHERE co.rootComment IS NOT NULL)")
    Long countCommentsAsweredByOthers();
    
    @Query("SELECT COUNT(c) FROM Comment c")
    Long countTotalComments();
}
