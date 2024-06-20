package com.api.zoo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ReplyCommentRequestDto {
    @NotBlank(message = "Message name must not be blank")
    @NotEmpty(message = "Message name must not be empty")
    private String message;
}
