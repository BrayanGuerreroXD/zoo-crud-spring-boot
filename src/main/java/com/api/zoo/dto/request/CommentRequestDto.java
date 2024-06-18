package com.api.zoo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CommentRequestDto {

    @NotBlank(message = "Message name must not be blank")
    @NotEmpty(message = "Message name must not be empty")
    private String message;

    @Positive(message = "User id must be greater than zero")
    @NotNull(message = "User id must not be null")
    private Long userId;

    @Positive(message = "Animal id must be greater than zero")
    @NotNull(message = "Animal id must not be null")
    private Long animalId;
}
