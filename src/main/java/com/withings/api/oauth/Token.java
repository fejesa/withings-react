package com.withings.api.oauth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {

    private final String accessToken;

    private final String tokenType;

    private final String scope;

    private final String refreshToken;

    private final int expiresIn;

    private final int userId;

    @JsonCreator
    public Token(@JsonProperty("access_token") String accessToken, @JsonProperty("token_type") String tokenType,
                 @JsonProperty("scope") String scope, @JsonProperty("refresh_token") String refreshToken,
                 @JsonProperty("expires_in") int expiresIn, @JsonProperty("userid") int userId) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.scope = scope;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getScope() {
        return scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public int getUserId() {
        return userId;
    }
}
