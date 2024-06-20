package com.api.zoo.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.api.zoo.dto.request.AnimalRequestDto;
import com.api.zoo.dto.response.AnimalResponseDto;
import com.api.zoo.dto.response.CountResponseDto;
import com.api.zoo.entity.Animal;
import com.api.zoo.entity.Species;
import com.api.zoo.exception.EntityNotFoundException;
import com.api.zoo.repository.AnimalRepository;
import com.api.zoo.service.AnimalService;
import com.api.zoo.service.SpeciesService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final SpeciesService speciesService;
    private final AnimalRepository animalRepository;

    private static final String ANIMAL_NOT_FOUND = "Animal with id %s not found";

    @Override
    public AnimalResponseDto getAnimalById(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Animal> animal = animalRepository.findById(id);
        if (Boolean.FALSE.equals(animal.isPresent()))
            throw new EntityNotFoundException(String.format(ANIMAL_NOT_FOUND, id));
        return modelMapper.map(animal, AnimalResponseDto.class);
    }

    @Override
    public List<AnimalResponseDto> getAllAnimals() {
        return animalRepository.findAll().stream().map(animal -> new ModelMapper().map(animal, AnimalResponseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public AnimalResponseDto createAnimal(AnimalRequestDto animalRequestDto) {
        ModelMapper modelMapper = new ModelMapper();
        Animal animal = modelMapper.map(animalRequestDto, Animal.class);

        Species species = speciesService.getSpeciesByIdEntity(animalRequestDto.getSpeciesId());

        animal.setId(null);
        animal.setSpecies(species);
        animal.setCreatedAt(LocalDateTime.now());

        return modelMapper.map(animalRepository.save(animal), AnimalResponseDto.class);
    }

    @Override
    @Transactional
    public AnimalResponseDto updateAnimal(Long id, AnimalRequestDto animalRequestDto) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Animal> animal = animalRepository.findById(id);

        Animal animalUpdated = modelMapper.map(animalRequestDto, Animal.class);

        if (Boolean.FALSE.equals(animal.isPresent()))
            throw new EntityNotFoundException(String.format(ANIMAL_NOT_FOUND, id));

        Species species = speciesService.getSpeciesByIdEntity(animalRequestDto.getSpeciesId());

        animalUpdated.setId(id);
        animalUpdated.setSpecies(species);
        animalUpdated.setCreatedAt(animal.get().getCreatedAt());
        animalUpdated.setUpdatedAt(LocalDateTime.now());

        return modelMapper.map(animalRepository.save(animalUpdated), AnimalResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteAnimal(Long id) {
        Optional<Animal> animal = animalRepository.findById(id);
        if (Boolean.FALSE.equals(animal.isPresent()))
            throw new EntityNotFoundException(String.format(ANIMAL_NOT_FOUND, id));
        animalRepository.delete(animal.get());
    }

    @Override
    public CountResponseDto countAnimalBySpeciesId(Long speciesId) {
        Integer count = animalRepository.countAnimalBySpeciesId(speciesId);
        return new CountResponseDto(count);
    }

    @Override
    public CountResponseDto countAnimalBySpeciesZoneId(Long zoneId) {
        Integer count = animalRepository.countAnimalBySpeciesZoneId(zoneId);
        return new CountResponseDto(count);
    }

    @Override
    public List<AnimalResponseDto> findAllByCreatedAtBetween(LocalDate registerDate) {
        LocalDateTime start = registerDate.atStartOfDay();
        LocalDateTime end = registerDate.atTime(LocalTime.MAX);
        return animalRepository.findAllByCreatedAtBetween(start, end).stream()
                .map(animal -> new ModelMapper().map(animal, AnimalResponseDto.class)).toList();
    }
    
}