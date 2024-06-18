package com.api.zoo.service;

import com.api.zoo.dto.request.UserRequestDto;
import com.api.zoo.dto.response.AuthResponseDto;

public interface AuthService {
    AuthResponseDto login(UserRequestDto request);

    void logout();
}
