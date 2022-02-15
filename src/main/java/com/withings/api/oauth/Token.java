package com.withings.api.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.myhealth.withings.api.WithingsException;

public class Token {

    private final String accessToken;

    private final String refreshToken;

    private final int expiresIn;

    public Token(String accessToken, String refreshToken, int expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public static Token fromString(String source) {
        try {
            var node = new ObjectMapper().readTree(source);
            var body = node.get("body");
            var accessToken = body.get("access_token").textValue();
            var refreshToken = body.get("refresh_token").textValue();
            var expiresIn = body.get("expires_in").asInt();
            return new Token(accessToken, refreshToken, expiresIn);
        } catch (JsonProcessingException e) {
            throw new WithingsException("Cannot process token", e);
        }
    }
}
