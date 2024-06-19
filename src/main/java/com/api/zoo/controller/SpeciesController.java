package com.api.zoo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.zoo.dto.request.SpeciesRequestDto;
import com.api.zoo.dto.response.SpeciesResponseDto;
import com.api.zoo.service.SpeciesService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/species")
@RequiredArgsConstructor
public class SpeciesController {
    
    private final SpeciesService speciesService;

    @GetMapping("/{id}")
    @RolesAllowed({"ADMIN", "EMPLEADO"})
    public ResponseEntity<SpeciesResponseDto> getSpeciesById(@PathVariable Long id) {
        return ResponseEntity.ok(speciesService.getSpeciesById(id));
    }

    @GetMapping
    @RolesAllowed({"ADMIN", "EMPLEADO"})
    public ResponseEntity<List<SpeciesResponseDto>> getAllSpecies() {
        return ResponseEntity.ok(speciesService.getAllSpecies());
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<SpeciesResponseDto> createSpecies(@Valid @RequestBody SpeciesRequestDto speciesRequestDto) {
        return ResponseEntity.ok(speciesService.createSpecies(speciesRequestDto));
    }

    @PutMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<SpeciesResponseDto> updateSpecies(@PathVariable Long id, @Valid @RequestBody SpeciesRequestDto speciesRequestDto) {
        return ResponseEntity.ok(speciesService.updateSpecies(id, speciesRequestDto));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> deleteSpecies(@PathVariable Long id) {
        speciesService.deleteSpecies(id);
        return ResponseEntity.noContent().build();
    }
}