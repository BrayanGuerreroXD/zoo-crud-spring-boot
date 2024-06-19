package com.api.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.zoo.entity.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
        
    Boolean existsAnimalByZoneId(Long zoneId);

    Boolean existsAnimalBySpeciesId(Long speciesId);

}