package com.api.zoo.service;

import java.util.List;

import com.api.zoo.dto.request.AnimalRequestDto;
import com.api.zoo.dto.response.AnimalResponseDto;

public interface AnimalService {
    
    AnimalResponseDto getAnimalById(Long id);

    List<AnimalResponseDto> getAllAnimals();

    AnimalResponseDto createAnimal(AnimalRequestDto animalRequestDto);

    AnimalResponseDto updateAnimal(Long id, AnimalRequestDto animalRequestDto);
    
    Boolean existsAnimalByZoneId(Long zoneId);

    Boolean existsAnimalBySpeciesId(Long speciesId);

    void deleteAnimal(Long id);

}