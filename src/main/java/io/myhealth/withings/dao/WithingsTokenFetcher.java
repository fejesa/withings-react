package io.myhealth.withings.dao;

import com.withings.api.oauth.Token;
import io.myhealth.withings.model.ApplicationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

@Component
@EnableScheduling
public class WithingsTokenFetcher implements TokenFetcher {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${withings.api.tokenHost}")
    private String tokenHost;

    @Value("${withings.api.clientId}")
    private String clientId;

    @Value("${withings.api.clientSecret}")
    private String clientSecret;

    @Value("${myhealth.withings.tokenFile}")
    private String tokenFile;

    private WebClient webClient;

    @Override
    @Scheduled(fixedRate = 2L * 3600 * 1000)
    public void refreshToken() {

        Mono<ApplicationToken> result = ApplicationToken
                .read(tokenFile)
                .flatMap(applicationToken -> webClient
                        .post()
                        .uri("/v2/oauth2")
                        .headers(h -> h.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                        .body(createBodyInserter(applicationToken.getRefreshToken()))
                        .exchange()
                        .flatMap(response -> response.bodyToMono(String.class))
                        .map(Token::fromString)
                        .map(t -> new ApplicationToken(t.getAccessToken(), t.getRefreshToken(), getExpirationTime(t.getExpiresIn())))
                        .log()
                        .subscribeOn(Schedulers.elastic())
                        .log()
                        .doOnSuccess(t -> log.info("Token successfully refreshed"))
                        .doOnError(e -> log.error("Token fetch error", e))
                );

        result.subscribe(this::writeToken);
    }

    private BodyInserters.MultipartInserter createBodyInserter(String token) {
        return BodyInserters.fromMultipartData(createRequestBody(token).build());
    }

    private MultipartBodyBuilder createRequestBody(String token) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("action", "requesttoken");
        builder.part("grant_type", "refresh_token");
        builder.part("client_id", clientId);
        builder.part("client_secret", clientSecret);
        builder.part("refresh_token", token);
        return builder;
    }

    @PostConstruct
    private void init() {
        webClient = WebClient.create(tokenHost);
    }

    private LocalDateTime getExpirationTime(int offset) {
        return LocalDateTime.now().plusSeconds(offset);
    }

    private void writeToken(ApplicationToken token) {
        try {
            ApplicationToken.writeToken(token, tokenFile);
        } catch (IOException e) {
            log.error("Token file error", e);
        }
    }
}
