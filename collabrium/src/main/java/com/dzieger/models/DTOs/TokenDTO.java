package com.dzieger.models.DTOs;

/**
 * TokenDTO
 *
 * This class is used to provide the data transfer object for the token
 * endpoint. It is used to provide the token to the endpoint.
 *
 * Example:
 * <pre>
 *     TokenDTO tokenDTO = new TokenDTO(token);
 * </pre>
 *
 * @version 1.0
 */
public class TokenDTO {

    private String token;

    /**
     * Default constructor
     */
    public TokenDTO() {
    }

    /**
     * Constructor
     *
     * @param token the token
     */
    public TokenDTO(String token) {
        this.token = token;
    }

    /**
     * getToken
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * setToken
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
