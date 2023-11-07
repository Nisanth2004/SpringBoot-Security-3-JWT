package com.nisanth.springsecurity.services;

import com.nisanth.springsecurity.dto.JwtAuthenticationResponse;
import com.nisanth.springsecurity.dto.RefreshTokenRequest;
import com.nisanth.springsecurity.dto.SigninRequest;
import com.nisanth.springsecurity.dto.SignupRequest;
import com.nisanth.springsecurity.entities.User;

public interface AuthenticationService
{
    User signup(SignupRequest signupRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
