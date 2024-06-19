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

import com.api.zoo.dto.request.AnimalRequestDto;
import com.api.zoo.dto.response.AnimalResponseDto;
import com.api.zoo.service.AnimalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {
    
    private final AnimalService animalService;

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponseDto> getAnimalById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }

    @GetMapping
    public ResponseEntity<List<AnimalResponseDto>> getAllAnimals() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }

    @PostMapping
    public ResponseEntity<AnimalResponseDto> createAnimal(@Valid @RequestBody AnimalRequestDto animalRequestDto) {
        return ResponseEntity.ok().body(animalService.createAnimal(animalRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponseDto> updateAnimal(@PathVariable Long id, @Valid @RequestBody AnimalRequestDto animalRequestDto) {
        return ResponseEntity.ok(animalService.updateAnimal(id, animalRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }
}