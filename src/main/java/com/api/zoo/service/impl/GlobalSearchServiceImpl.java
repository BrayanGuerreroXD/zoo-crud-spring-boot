package com.api.zoo.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.api.zoo.dto.response.AnimalResponseDto;
import com.api.zoo.dto.response.CommentResponseDto;
import com.api.zoo.dto.response.GlobalSearchResponseDto;
import com.api.zoo.dto.response.ReplyCommentResponseDto;
import com.api.zoo.dto.response.SpeciesResponseDto;
import com.api.zoo.dto.response.ZoneResponseDto;
import com.api.zoo.exception.KeywordNullOrBlankException;
import com.api.zoo.service.AnimalService;
import com.api.zoo.service.CommentService;
import com.api.zoo.service.GlobalSearchService;
import com.api.zoo.service.SpeciesService;
import com.api.zoo.service.ZoneService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GlobalSearchServiceImpl implements GlobalSearchService {

    private final ZoneService zoneService;
    private final SpeciesService speciesService;
    private final AnimalService animalService;
    private final CommentService commentService;

    @Override
    public GlobalSearchResponseDto globalSearching(String keyword) {
        GlobalSearchResponseDto globalSearchResponseDto = new GlobalSearchResponseDto();

        if (Objects.isNull(keyword) || keyword.isBlank())
            throw new KeywordNullOrBlankException();

        List<ZoneResponseDto> zones = zoneService.findByNameMatch(keyword);
        List<SpeciesResponseDto> species = speciesService.findByNameMatch(keyword);
        List<AnimalResponseDto> animals = animalService.findByNameMatch(keyword);
        List<CommentResponseDto> comments = commentService.findAllCommentByMessageMatch(keyword);
        List<ReplyCommentResponseDto> replies = commentService.findAllReplyCommentByMessageMatch(keyword);

        globalSearchResponseDto.setZones(zones);
        globalSearchResponseDto.setSpecies(species);
        globalSearchResponseDto.setAnimals(animals);
        globalSearchResponseDto.setComments(comments);
        globalSearchResponseDto.setReplies(replies);

        return globalSearchResponseDto;
    }
    
}
