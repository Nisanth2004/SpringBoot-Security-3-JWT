package com.nisanth.springsecurity.controller;

import com.nisanth.springsecurity.dto.JwtAuthenticationResponse;
import com.nisanth.springsecurity.dto.RefreshTokenRequest;
import com.nisanth.springsecurity.dto.SigninRequest;
import com.nisanth.springsecurity.dto.SignupRequest;
import com.nisanth.springsecurity.entities.User;
import com.nisanth.springsecurity.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController
{
    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest signupRequest)
    {
         return ResponseEntity.ok(authenticationService.signup(signupRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest)
    {
      return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
