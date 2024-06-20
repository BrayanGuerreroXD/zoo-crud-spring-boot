package com.api.zoo.service;

import com.api.zoo.dto.request.UserRequestDto;
import com.api.zoo.dto.response.UserResponseDto;
import com.api.zoo.entity.User;

public interface UserService {

    User getUserById(Long id);
    
    UserResponseDto saveUser(UserRequestDto userRequestDto);
}
