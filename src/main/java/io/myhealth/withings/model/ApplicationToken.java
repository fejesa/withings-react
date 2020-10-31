package io.myhealth.withings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class ApplicationToken {

    private final String accessToken;

    private final String refreshToken;

    private final LocalDateTime expirationTime;

    @JsonCreator
    public ApplicationToken(@JsonProperty("accessToken") String accessToken,
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

    public static Mono<ApplicationToken> read(String tokenFile) {
        Flux<DataBuffer> dataBuffer = DataBufferUtils.read(
                new DefaultResourceLoader().getResource(tokenFile),
                new DefaultDataBufferFactory(),
                4096);
        return new Jackson2JsonDecoder()
                .decodeToMono(dataBuffer, ResolvableType.forClass(ApplicationToken.class), null, null)
                .map(o -> (ApplicationToken)o);
    }

    public static void writeToken(ApplicationToken token, String file) throws IOException {
        Path path = Paths.get(URI.create(file)).toAbsolutePath();
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
