package com.dzieger.models.DTOs;

import jakarta.validation.constraints.NotBlank;

public class RequestRefreshTokenDTO {

    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }



}
