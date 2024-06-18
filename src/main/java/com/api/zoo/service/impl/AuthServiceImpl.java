package com.api.zoo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.api.zoo.dto.request.UserRequestDto;
import com.api.zoo.dto.response.AuthResponseDto;
import com.api.zoo.entity.User;
import com.api.zoo.jwt.JwtService;
import com.api.zoo.repository.UserRepository;
import com.api.zoo.service.AuthService;
import com.api.zoo.service.TokenService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponseDto login(UserRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        User userEntity = (User) user;

        Map<String, Object> extra = new HashMap<>();
        extra.put("role", userEntity.getRole().getName());
        extra.put("id", userEntity.getId());

        String token = jwtService.getToken(extra, user);

        // Save token in database to validate it later
        tokenService.saveToken(token, userEntity.getId());

        return AuthResponseDto.builder()
            .token(token)
            .build();
    }
    
    @Transactional
    public void logout() {
        final String token = tokenService.getToken();
        Long userId = tokenService.getUserIdByToken(token);
        tokenService.removeTokenByValue(token.replace("Bearer", "").trim(), userId);
    }
    
}
