package io.myhealth.withings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class WithingsToken {

    private final String accessToken;

    private final String refreshToken;

    private final LocalDateTime expirationTime;

    @JsonCreator
    public WithingsToken(@JsonProperty("accessToken") String accessToken,
                         @JsonProperty("refreshToken") String refreshToken, @JsonProperty("expirationTime") LocalDateTime expirationTime) {
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

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public static WithingsToken empty() {
        return new WithingsToken("", "", null);
    }

    public static WithingsToken read(String tokenFile) throws IOException {
        Path path = Paths.get(tokenFile).toAbsolutePath();
        return Files.exists(path) ? getObjectMapper().readValue(path.toFile(), WithingsToken.class) :WithingsToken.empty();
    }

    public static void writeToken(WithingsToken token, String file) throws IOException {
        Path path = Paths.get(file).toAbsolutePath();
        String content = getObjectMapper().writeValueAsString(token);
        Files.writeString(path, content, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}