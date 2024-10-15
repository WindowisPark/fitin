package com.fitin.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSignupRequest {
    private String membername;
    private String email;
    private String password;
}