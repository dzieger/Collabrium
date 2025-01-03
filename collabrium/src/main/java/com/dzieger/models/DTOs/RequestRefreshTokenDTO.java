package com.dzieger.models.DTOs;

import jakarta.validation.constraints.NotBlank;

/**
 * RequestRefreshTokenDTO
 *
 * This class is used to provide the data transfer object for the request
 * refresh token endpoint. It is used to provide the refresh token to the
 * endpoint.
 *
 * @version 1.0
 */
public class RequestRefreshTokenDTO {

    @NotBlank
    private String refreshToken;

    /**
     * Constructor
     */
    public RequestRefreshTokenDTO() {
        // Default constructor
    }

    /**
     * getRefreshToken
     *
     * @return the refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * setRefreshToken
     *
     * @param refreshToken the refresh token
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }



}
