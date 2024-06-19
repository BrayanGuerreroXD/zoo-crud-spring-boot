package com.api.zoo.service;

import java.util.List;

import com.api.zoo.dto.request.ZoneRequestDto;
import com.api.zoo.dto.response.ZoneResponseDto;
import com.api.zoo.entity.Zone;

public interface ZoneService {
    ZoneResponseDto getZoneById(Long id);

    Zone getZoneByIdEntity(Long id);

    List<ZoneResponseDto> getAllZones();

    ZoneResponseDto createZone(ZoneRequestDto zoneRequestDto);

    ZoneResponseDto updateZone(Long id, ZoneRequestDto zoneRequestDto);

    void deleteZone(Long id);
}
