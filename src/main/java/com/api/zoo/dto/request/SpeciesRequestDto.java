package com.api.zoo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SpeciesRequestDto {
    
    @NotBlank(message = "Species name must not be blank")
    @NotEmpty(message = "Species name must not be empty")
    private String name;

    @Positive(message = "Zone id must be greater than zero")
    @NotNull(message = "Zone id must not be null")
    private Long zoneId;
}
