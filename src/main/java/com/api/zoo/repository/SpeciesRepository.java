package com.api.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.zoo.entity.Species;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
    Boolean existsByName(String name);
}
