package com.api.zoo.dto.response;

import lombok.Data;

@Data
public class AnimalResponseDto {
    private Long id;
    private String name;
    private SpeciesResponseDto species;
}
