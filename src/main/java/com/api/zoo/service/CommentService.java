package com.api.zoo.service;

import com.api.zoo.dto.request.CommentRequestDto;
import com.api.zoo.dto.request.ReplyCommentRequestDto;
import com.api.zoo.dto.response.CommentResponseDto;
import com.api.zoo.dto.response.ReplyCommentResponseDto;

public interface CommentService {
    
    CommentResponseDto createComment(CommentRequestDto commentRequestDto);

    ReplyCommentResponseDto replyComment(Long id, ReplyCommentRequestDto answerRequestDto);
}