package io.github.cynergy.authservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {
    private String token;

    public TokenResponse(@JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
