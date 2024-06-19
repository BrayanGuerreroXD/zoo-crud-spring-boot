package com.api.zoo.service;

import java.util.List;

import com.api.zoo.dto.request.SpeciesRequestDto;
import com.api.zoo.dto.response.SpeciesResponseDto;

public interface SpeciesService {
    SpeciesResponseDto getSpeciesById(Long id);

    List<SpeciesResponseDto> getAllSpecies();

    SpeciesResponseDto createSpecies(SpeciesRequestDto speciesRequestDto);

    SpeciesResponseDto updateSpecies(Long id, SpeciesRequestDto speciesRequestDto);

    void deleteSpecies(Long id);
}
