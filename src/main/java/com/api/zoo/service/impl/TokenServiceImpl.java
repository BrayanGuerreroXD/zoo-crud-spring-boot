package com.api.zoo.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.api.zoo.entity.Token;
import com.api.zoo.entity.User;
import com.api.zoo.exception.InvalidTokenPrefixException;
import com.api.zoo.exception.JwtAuthException;
import com.api.zoo.exception.TokenNullException;
import com.api.zoo.jwt.JwtService;
import com.api.zoo.repository.TokenRepository;
import com.api.zoo.service.TokenService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService { 
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    
    private static final String BEARER_TOKEN_PREFIX ="Bearer ";

    @Value("${number.sessions}")
    private Integer numberSessions;

    @Override
    public String getToken() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                       .map(ServletRequestAttributes::getRequest)
                       .map(request -> request.getHeader("Authorization"))
                       .orElse(null);
    }

    @Override
    public Long getUserIdByToken(String token) {
        validateToken(token);
        return jwtService.getUserIdFromToken(removeBearerPrefix(token));
    }

    @Override
    public String getUserRoleByToken(String token) {
        validateToken(token);
        return jwtService.getRoleFromToken(removeBearerPrefix(token));
    }

    @Override
    @Transactional
    public void saveToken(String jwtToken, Long userId) {
        List<Token> tokens = tokenRepository.findAllByUserId(userId);

        // Remove expired tokens
        tokens.removeIf(token -> {
            if (jwtService.isTokenExpired(token.getValue())) {
                this.removeTokenByValue(token.getValue(), userId);
                return true;
            }
            return false;
        });

        if (tokens.size() >= numberSessions) 
            throw new JwtAuthException("User has more than one token");

        Token token = new Token();
        User user = new User();

        user.setId(userId);
        token.setValue(removeBearerPrefix(jwtToken));
        token.setUser(user);

        tokenRepository.save(token);
    }
    
    @Override
    @Transactional
    public void validateJwtToken(String token, Long userId) {
        if (Boolean.FALSE.equals(tokenRepository.existsByValueAndUserId(token, userId)))
            throw new JwtAuthException("Token is not valid");
        if (jwtService.isTokenExpired(token)) {
            // Only return a exception because the login filter will remove the token from the database
            throw new JwtAuthException("Token is expired");
        }
    }

    @Override
    @Transactional
    public void removeTokenByValue(String value, Long userId) {
        tokenRepository.deleteByValueAndUserId(value, userId);
    }

    private void validateToken(String token) {
        if (Objects.isNull(token)) 
            throw new TokenNullException();
            
        if (!token.startsWith(BEARER_TOKEN_PREFIX))
            throw new InvalidTokenPrefixException();
    }

    private String removeBearerPrefix(String token) {
        return token.replace(BEARER_TOKEN_PREFIX, "");
    }
}