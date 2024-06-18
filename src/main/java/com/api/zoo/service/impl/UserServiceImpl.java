package com.api.zoo.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.zoo.dto.request.UserRequestDto;
import com.api.zoo.dto.response.UserResponseDto;
import com.api.zoo.entity.User;
import com.api.zoo.enums.RoleEnum;
import com.api.zoo.exception.EmailAlreadyExistsException;
import com.api.zoo.repository.UserRepository;
import com.api.zoo.service.RoleService;
import com.api.zoo.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        ModelMapper modelMapper = new ModelMapper();

        if (Boolean.TRUE.equals(userRepository.existsByEmail(userRequestDto.getEmail())))
            throw new EmailAlreadyExistsException();

        User user = modelMapper.map(userRequestDto, User.class);
        user.setRole(roleService.findByName(RoleEnum.EMPLEADO.getRole()));
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        // Mapping name from Role to UserResponseDto.role
        TypeMap<User, UserResponseDto> typeMap = modelMapper.createTypeMap(User.class, UserResponseDto.class);
        typeMap.addMappings(mapper -> mapper.map(src -> src.getRole().getName(), UserResponseDto::setRole));

        return modelMapper.map( userRepository.save(user) , UserResponseDto.class);
    }
    
}