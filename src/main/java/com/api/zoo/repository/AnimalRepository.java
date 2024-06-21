package com.api.zoo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.zoo.entity.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
        
    Boolean existsAnimalBySpeciesZoneId(Long zoneId);

    Boolean existsAnimalBySpeciesId(Long speciesId);

    Integer countAnimalBySpeciesId(Long speciesId);

    Integer countAnimalBySpeciesZoneId(Long zoneId);

    List<Animal> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Animal> findByNameContainingIgnoreCase(String name);

}