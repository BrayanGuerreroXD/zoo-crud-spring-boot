package com.api.zoo.service;

import com.api.zoo.dto.request.UserRequestDto;
import com.api.zoo.dto.response.UserResponseDto;

public interface UserService {
    
    UserResponseDto saveUser(UserRequestDto userRequestDto);
}
