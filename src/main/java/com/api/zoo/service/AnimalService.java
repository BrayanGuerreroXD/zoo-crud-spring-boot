package com.api.zoo.service;

import java.time.LocalDate;
import java.util.List;

import com.api.zoo.dto.request.AnimalRequestDto;
import com.api.zoo.dto.response.AnimalResponseDto;
import com.api.zoo.dto.response.CountResponseDto;
import com.api.zoo.entity.Animal;

public interface AnimalService {
    
    Animal getAnimalEntityById(Long id);

    AnimalResponseDto getAnimalById(Long id);

    List<AnimalResponseDto> getAllAnimals();

    AnimalResponseDto createAnimal(AnimalRequestDto animalRequestDto);

    AnimalResponseDto updateAnimal(Long id, AnimalRequestDto animalRequestDto);

    void deleteAnimal(Long id);

    CountResponseDto countAnimalBySpeciesId(Long speciesId);

    CountResponseDto countAnimalBySpeciesZoneId(Long zoneId);

    List<AnimalResponseDto> findAllByCreatedAtBetween(LocalDate registerDate);

    List<AnimalResponseDto> findByNameMatch(String name);

}