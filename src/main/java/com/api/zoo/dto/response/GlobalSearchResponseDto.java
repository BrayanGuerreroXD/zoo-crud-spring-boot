package com.api.zoo.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class GlobalSearchResponseDto {
    private List<ZoneResponseDto> zones;
    private List<AnimalResponseDto> animals;
    private List<SpeciesResponseDto> species;
    private List<CommentResponseDto> comments;
    private List<ReplyCommentResponseDto> replies;
}