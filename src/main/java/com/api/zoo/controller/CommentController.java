package com.api.zoo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.zoo.dto.request.CommentRequestDto;
import com.api.zoo.dto.request.ReplyCommentRequestDto;
import com.api.zoo.dto.response.CommentResponseDto;
import com.api.zoo.dto.response.ReplyCommentResponseDto;
import com.api.zoo.service.CommentService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@RolesAllowed({"ADMIN", "EMPLEADO"})
public class CommentController {
    
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok().body(commentService.createComment(commentRequestDto));
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<ReplyCommentResponseDto> replyComment(@PathVariable Long id, @Valid @RequestBody ReplyCommentRequestDto answerRequestDto) {
        return ResponseEntity.ok().body(commentService.replyComment(id, answerRequestDto));
    }

}