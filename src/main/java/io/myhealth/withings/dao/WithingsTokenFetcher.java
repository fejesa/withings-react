package io.myhealth.withings.dao;

import com.withings.api.oauth.Token;
import io.myhealth.withings.api.WithingsException;
import io.myhealth.withings.model.WithingsToken;
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
        try {
            WithingsToken token = WithingsToken.read(tokenFile);

            Mono<WithingsToken> result = webClient
                    .post()
                    .uri("/oauth2/token")
                    .headers(h -> h.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .body(createBodyInserter(token))
                    .exchange()
                    .flatMap(response -> response.bodyToMono(Token.class))
                    .map(t -> new WithingsToken(t.getAccessToken(), t.getRefreshToken(), getExpirationTime(t.getExpiresIn())))
                    .subscribeOn(Schedulers.elastic())
                    .doOnNext(this::writeToken)
                    .doOnSuccess(t -> log.info("Token successfully refreshed"))
                    .doOnError(e -> log.error("Token fetch error", e));

            result.subscribe();

        } catch (IOException e) {
            log.error("Withing token refresh error", e);
            throw new WithingsException("Token refresh error");
        }
    }

    private BodyInserters.MultipartInserter createBodyInserter(WithingsToken withingsToken) {
        return BodyInserters.fromMultipartData(createRequestBody(withingsToken).build());
    }

    private MultipartBodyBuilder createRequestBody(WithingsToken token) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("grant_type", "refresh_token");
        builder.part("client_id", clientId);
        builder.part("client_secret", clientSecret);
        builder.part("refresh_token", token.getRefreshToken());
        return builder;
    }

    @PostConstruct
    private void init() {
        webClient = WebClient.create(tokenHost);
    }

    private LocalDateTime getExpirationTime(int offset) {
        return LocalDateTime.now().plusSeconds(offset);
    }

    private void writeToken(WithingsToken token) {
        try {
            WithingsToken.writeToken(token, tokenFile);
        } catch (IOException e) {
            log.error("Token file error", e);
        }
    }
}