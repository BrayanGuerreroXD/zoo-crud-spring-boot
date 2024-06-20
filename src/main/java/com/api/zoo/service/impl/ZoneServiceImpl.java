package com.api.zoo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.zoo.dto.request.ZoneRequestDto;
import com.api.zoo.dto.response.ZoneResponseDto;
import com.api.zoo.entity.Zone;
import com.api.zoo.exception.EntityNotFoundException;
import com.api.zoo.exception.ZoneNameAlreadyExistsException;
import com.api.zoo.exception.ZoneWithAnimalsException;
import com.api.zoo.repository.AnimalRepository;
import com.api.zoo.repository.ZoneRepository;
import com.api.zoo.service.ZoneService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {
    private final ZoneRepository zoneRepository;
    private final AnimalRepository animalRepository;

    private static final String ZONE_NOT_FOUND = "Zone with id %s not found";

    @Override
    public ZoneResponseDto getZoneById(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Zone> zone = zoneRepository.findById(id);
        if (Boolean.FALSE.equals(zone.isPresent()))
            throw new EntityNotFoundException(String.format(ZONE_NOT_FOUND, id));
        return modelMapper.map(zone, ZoneResponseDto.class);
    }

    @Override
    public Zone getZoneByIdEntity(Long id) {
        return zoneRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(ZONE_NOT_FOUND, id)));
    }

    @Override
    public List<ZoneResponseDto> getAllZones() {
        return zoneRepository.findAll().stream().map(zone -> new ModelMapper().map(zone, ZoneResponseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public ZoneResponseDto createZone(ZoneRequestDto zoneRequestDto) {
        Zone zone = new ModelMapper().map(zoneRequestDto, Zone.class);
        if (Boolean.TRUE.equals(zoneRepository.existsByName(zone.getName())))
            throw new ZoneNameAlreadyExistsException();
        zone.setCreatedAt(LocalDateTime.now());
        return new ModelMapper().map(zoneRepository.save(zone), ZoneResponseDto.class);
    }

    @Override
    @Transactional
    public ZoneResponseDto updateZone(Long id, ZoneRequestDto zoneRequestDto) {
        Optional<Zone> zone = zoneRepository.findById(id);
        if (Boolean.FALSE.equals(zone.isPresent()))
            throw new EntityNotFoundException(String.format(ZONE_NOT_FOUND, id));
        if (!Objects.equals(zone.get().getName(), zoneRequestDto.getName())
                && Boolean.TRUE.equals(zoneRepository.existsByName(zoneRequestDto.getName())))
            throw new ZoneNameAlreadyExistsException();

        Zone updatedZone = zone.get();
        updatedZone.setName(zoneRequestDto.getName());
        updatedZone.setUpdatedAt(LocalDateTime.now());

        return new ModelMapper().map(zoneRepository.save(updatedZone), ZoneResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteZone(Long id) {
        Optional<Zone> zone = zoneRepository.findById(id);
        if (Boolean.FALSE.equals(zone.isPresent()))
            throw new EntityNotFoundException(String.format(ZONE_NOT_FOUND, id));
        if (Boolean.TRUE.equals(animalRepository.existsAnimalBySpeciesZoneId(id))) 
            throw new ZoneWithAnimalsException();
        zoneRepository.delete(zone.get());
    }
    
}
