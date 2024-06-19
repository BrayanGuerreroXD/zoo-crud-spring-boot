package com.api.zoo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.api.zoo.dto.request.SpeciesRequestDto;
import com.api.zoo.dto.response.SpeciesResponseDto;
import com.api.zoo.entity.Species;
import com.api.zoo.entity.Zone;
import com.api.zoo.exception.EntityNotFoundException;
import com.api.zoo.exception.SpeciesNameAlreadyExistsException;
import com.api.zoo.repository.SpeciesRepository;
import com.api.zoo.service.AnimalService;
import com.api.zoo.service.SpeciesService;
import com.api.zoo.service.ZoneService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpeciesServiceImpl implements SpeciesService {

    private final SpeciesRepository speciesRepository;
    private final ZoneService zoneService;
    private final AnimalService animalService;

    private static final String SPECIES_NOT_FOUND = "Species with id %s not found";

    @Override
    public SpeciesResponseDto getSpeciesById(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Species> species = speciesRepository.findById(id);
        if (Boolean.FALSE.equals(species.isPresent()))
            throw new EntityNotFoundException(String.format(SPECIES_NOT_FOUND, id));
        return modelMapper.map(species, SpeciesResponseDto.class);   
    }

    @Override
    public List<SpeciesResponseDto> getAllSpecies() {
        return speciesRepository.findAll().stream().map(species -> new ModelMapper().map(species, SpeciesResponseDto.class))
                .toList();
    }

    @Override
    public SpeciesResponseDto createSpecies(SpeciesRequestDto speciesRequestDto) {
        ModelMapper modelMapper = new ModelMapper();
        Species species = modelMapper.map(speciesRequestDto, Species.class);

        if (Boolean.TRUE.equals(speciesRepository.existsByName(species.getName())))
            throw new SpeciesNameAlreadyExistsException();

        Zone zone = zoneService.getZoneByIdEntity(speciesRequestDto.getZoneId());
        
        species.setId(null);
        species.setZone(zone);
        species.setCreatedAt(LocalDateTime.now());

        Species speciesEntity = speciesRepository.save(species);

        return modelMapper.map(speciesEntity, SpeciesResponseDto.class);
    }

    @Override
    public SpeciesResponseDto updateSpecies(Long id, SpeciesRequestDto speciesRequestDto) {
        Optional<Species> species = speciesRepository.findById(id);
        if (Boolean.FALSE.equals(species.isPresent()))
            throw new EntityNotFoundException(String.format(SPECIES_NOT_FOUND, id));

        ModelMapper modelMapper = new ModelMapper();
        Species speciesEntity = modelMapper.map(speciesRequestDto, Species.class);

        if (!species.get().getName().equals(speciesRequestDto.getName())
                && Boolean.TRUE.equals(speciesRepository.existsByName(speciesRequestDto.getName())))
            throw new SpeciesNameAlreadyExistsException();

        Zone zone = zoneService.getZoneByIdEntity(speciesRequestDto.getZoneId());
        speciesEntity.setId(id);
        speciesEntity.setZone(zone);
        speciesEntity.setCreatedAt(species.get().getCreatedAt());
        speciesEntity.setUpdatedAt(LocalDateTime.now());

        return modelMapper.map(speciesRepository.save(speciesEntity), SpeciesResponseDto.class);
    }

    @Override
    public void deleteSpecies(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteSpecies'");
    }
    
}
