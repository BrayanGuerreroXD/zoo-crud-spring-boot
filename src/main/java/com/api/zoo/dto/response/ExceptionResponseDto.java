package com.api.zoo.dto.response;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ExceptionResponseDto {
    private HttpStatus status;
    private String message;
}
