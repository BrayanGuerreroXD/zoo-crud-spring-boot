package com.api.zoo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ZoneRequestDto {
    
    @NotBlank(message = "Zone name must not be blank")
    @NotEmpty(message = "Zone name must not be empty")
    private String name;
}