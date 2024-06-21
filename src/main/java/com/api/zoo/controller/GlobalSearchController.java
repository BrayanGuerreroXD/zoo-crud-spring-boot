package com.api.zoo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.zoo.dto.response.GlobalSearchResponseDto;
import com.api.zoo.service.GlobalSearchService;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("global-search")
@RolesAllowed({"ADMIN", "EMPLEADO"})
@RequiredArgsConstructor
public class GlobalSearchController {

    private final GlobalSearchService globalSearchService;

    @GetMapping
    public GlobalSearchResponseDto globalSearching(@RequestParam(name = "keyword") String keyword) {
        return globalSearchService.globalSearching(keyword);
    }

}