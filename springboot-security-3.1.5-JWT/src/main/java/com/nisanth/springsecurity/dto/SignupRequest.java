package com.nisanth.springsecurity.dto;

import lombok.Data;

@Data
public class SignupRequest
{
    private String firstName;
    private String lastName;
    private String email;
    private String password;


}
