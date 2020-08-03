package io.myhealth.withings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WithingsToken {

    private final String accessToken;

    private final String refreshToken;

    private final long expirationTime;

    @JsonCreator
    public WithingsToken(@JsonProperty("accessToken") String accessToken,
                         @JsonProperty("refreshToken") String refreshToken, @JsonProperty("expirationTime") long expirationTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expirationTime = expirationTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public static WithingsToken empty() {
        return new WithingsToken("", "", 0);
    }
}