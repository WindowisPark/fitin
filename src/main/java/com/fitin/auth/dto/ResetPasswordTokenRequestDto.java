package com.fitin.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordTokenRequestDto {
    private String token;
    private String newPassword;

    // Lombok의 @Getter와 @Setter 어노테이션으로 인해 아래의 메서드들이 자동 생성됩니다.
    // public String getToken() { return token; }
    // public void setToken(String token) { this.token = token; }
    // public String getNewPassword() { return newPassword; }
    // public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}