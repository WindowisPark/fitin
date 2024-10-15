package com.fitin.auth.dto;

import lombok.Data;

@Data
public class ResetPasswordRequestDto {
    private String email;
    // 또는 private String userId;
}