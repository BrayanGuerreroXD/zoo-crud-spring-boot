package com.api.zoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.zoo.entity.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Boolean existsByName(String name);
}