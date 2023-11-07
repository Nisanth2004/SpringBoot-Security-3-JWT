package com.nisanth.springsecurity.services;

import com.nisanth.springsecurity.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public interface JwtService
{
    String extractuserName(String token);
    String generateToken(UserDetails userDetails);
    boolean istokenValid(String token,UserDetails userDetails);


    String generateRefreshToken(HashMap<String, Object> extractClaims, UserDetails userDetails);
}
