package com.fitin.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class AdminSignupResponse {
    private String message;
    private String adminUsername;
}