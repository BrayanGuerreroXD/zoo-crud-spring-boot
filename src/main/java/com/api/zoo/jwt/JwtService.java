package com.api.zoo.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.api.zoo.exception.InvalidTokenException;
import com.api.zoo.exception.TokenParsingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationTime;

    public String getToken(Map<String,Object> extraClaims, UserDetails user) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
            .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()))
            .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            String email = getUsernameFromToken(token);
            String role = getRoleFromToken(token);
            Collection<SimpleGrantedAuthority> authorities =
                Arrays.stream(("ROLE_" + role.toUpperCase()).split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(email, null, authorities);
        } catch (JwtException e){
            return null;
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Long getUserIdFromToken(String token) {
        Integer idInteger = getClaim(token, claims -> claims.get("id", Integer.class));
        return idInteger != null ? idInteger.longValue() : null;
    }    

    public String getRoleFromToken(String token) {
        return getClaim(token, claims -> claims.get("role", String.class));
    }

    public Boolean isTokenExpired(String token) {
        try {
            Date expiredDate = getClaim(token, Claims::getExpiration);
            return expiredDate.before(new Date());
        } catch (SignatureException e) {
            throw new InvalidTokenException();
        } catch (IllegalArgumentException e) {
            throw new TokenParsingException();
        }
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (JwtException e) {
            return null;
        }
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }
}