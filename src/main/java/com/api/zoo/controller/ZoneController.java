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

import com.api.zoo.dto.request.ZoneRequestDto;
import com.api.zoo.dto.response.ZoneResponseDto;
import com.api.zoo.service.ZoneService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/zones")
@RequiredArgsConstructor
public class ZoneController {
    private final ZoneService zoneService;

    @GetMapping("/{id}")
    @RolesAllowed({"ADMIN", "EMPLEADO"})
    public ResponseEntity<ZoneResponseDto> getZoneById(@PathVariable Long id) {
        return ResponseEntity.ok(zoneService.getZoneById(id));
    }

    @GetMapping
    @RolesAllowed({"ADMIN", "EMPLEADO"})
    public ResponseEntity<List<ZoneResponseDto>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<ZoneResponseDto> createZone(@Valid @RequestBody ZoneRequestDto zoneRequestDto) {
        return ResponseEntity.ok(zoneService.createZone(zoneRequestDto));
    }

    @PutMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<ZoneResponseDto> updateZone(@PathVariable Long id, @Valid @RequestBody ZoneRequestDto zoneRequestDto) {
        return ResponseEntity.ok(zoneService.updateZone(id, zoneRequestDto));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}