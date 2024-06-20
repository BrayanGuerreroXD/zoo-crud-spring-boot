package com.api.zoo.dto.response;

import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String message;
    private AnimalResponseDto animal;
}