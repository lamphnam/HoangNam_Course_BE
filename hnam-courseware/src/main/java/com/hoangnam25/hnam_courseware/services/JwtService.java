package com.hoangnam25.hnam_courseware.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(String username);

    Claims extractClaims(String token);

    String extractUsername(String token);

    boolean validateToken(String username, UserDetails userDetails, String token);

    boolean isTokenExpired(String token);
}
