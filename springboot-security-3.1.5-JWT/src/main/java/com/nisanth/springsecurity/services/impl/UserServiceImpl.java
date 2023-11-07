package com.nisanth.springsecurity.services.impl;

import com.nisanth.springsecurity.repository.UserRepository;
import com.nisanth.springsecurity.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService

{
    private final UserRepository userRepository;


    @Override
    public UserDetailsService UserDetailsService() {
        return new UserDetailsService() {

                    @Override
                    public UserDetails loadUserByUsername(String username)  {
                        return userRepository.findByEmail(username)
                                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

            }
        };
    }
}
