package com.api.zoo.service;

import com.api.zoo.dto.response.GlobalSearchResponseDto;

public interface GlobalSearchService {
    GlobalSearchResponseDto globalSearching(String keyword);
}
