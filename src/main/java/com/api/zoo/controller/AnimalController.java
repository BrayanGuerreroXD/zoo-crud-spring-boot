package com.api.zoo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.zoo.dto.request.AnimalRequestDto;
import com.api.zoo.dto.response.AnimalResponseDto;
import com.api.zoo.service.AnimalService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AnimalController {
    
    private final AnimalService animalService;

    @GetMapping("/animal/{id}")
    @RolesAllowed({"ADMIN", "EMPLEADO"})
    public ResponseEntity<AnimalResponseDto> getAnimalById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }

    @GetMapping("/animals")
    @RolesAllowed({"ADMIN", "EMPLEADO"})
    public ResponseEntity<List<AnimalResponseDto>> getAllAnimals() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }

    @PostMapping("/animal")
    @RolesAllowed("ADMIN")
    public ResponseEntity<AnimalResponseDto> createAnimal(@Valid @RequestBody AnimalRequestDto animalRequestDto) {
        return ResponseEntity.ok().body(animalService.createAnimal(animalRequestDto));
    }

    @PutMapping("/animal/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<AnimalResponseDto> updateAnimal(@PathVariable Long id, @Valid @RequestBody AnimalRequestDto animalRequestDto) {
        return ResponseEntity.ok(animalService.updateAnimal(id, animalRequestDto));
    }

    @DeleteMapping("/animal/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok().build();
    }
}