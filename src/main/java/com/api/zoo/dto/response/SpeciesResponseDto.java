package com.api.zoo.dto.response;

import lombok.Data;

@Data
public class SpeciesResponseDto {
    private Long id;
    private String name;
    private ZoneResponseDto zone;
}
