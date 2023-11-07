package com.nisanth.springsecurity.services.impl;

import com.nisanth.springsecurity.dto.JwtAuthenticationResponse;
import com.nisanth.springsecurity.dto.RefreshTokenRequest;
import com.nisanth.springsecurity.dto.SigninRequest;
import com.nisanth.springsecurity.dto.SignupRequest;
import com.nisanth.springsecurity.entities.Role;
import com.nisanth.springsecurity.entities.User;
import com.nisanth.springsecurity.repository.UserRepository;
import com.nisanth.springsecurity.services.AuthenticationService;
import com.nisanth.springsecurity.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
private final AuthenticationManager authenticationManager;
private final JwtService jwtService;
    public User signup(SignupRequest signupRequest)
    {
        User user=new User();
        user.setFirstname(signupRequest.getFirstName());
        user.setEmail(signupRequest.getEmail());
        user.setSecondname(signupRequest.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

         return  userRepository.save(user);


    }
    // validate already user
    public JwtAuthenticationResponse signin(SigninRequest signinRequest)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),
                signinRequest.getPassword()));

        var user=userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid Username or password"));
   var jwt=jwtService.generateToken(user);
        var refreshToken=jwtService.generateRefreshToken(new HashMap<>(),user);

        JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest)
    {
        String userEmail=jwtService.extractuserName(refreshTokenRequest.getToken());
        User user=userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.istokenValid(refreshTokenRequest.getToken(),user))
        {
            var jwt=jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
