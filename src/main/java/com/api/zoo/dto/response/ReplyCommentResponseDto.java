package com.api.zoo.dto.response;

import lombok.Data;

@Data
public class ReplyCommentResponseDto {
    private Long id;
    private String message;
    private CommentResponseDto comment;
}
