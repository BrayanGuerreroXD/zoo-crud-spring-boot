package com.api.zoo.service;

public interface TokenService {
    String getToken();
    
    Long getUserIdByToken(String token);

    String getUserRoleByToken(String token);

    void saveToken(String jwtToken, Long userId);

    void validateJwtToken(String token, Long userId);

    void removeTokenByValue(String value, Long userId);
}
