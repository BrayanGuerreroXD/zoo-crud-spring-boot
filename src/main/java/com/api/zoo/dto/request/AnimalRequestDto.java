package com.api.zoo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AnimalRequestDto {
    
    @NotBlank(message = "Animal name must not be blank")
    @NotEmpty(message = "Animal name must not be empty")
    private String name;

    @Positive(message = "Species id must be greater than zero")
    @NotNull(message = "Species id must not be null")
    private Long speciesId;
}
