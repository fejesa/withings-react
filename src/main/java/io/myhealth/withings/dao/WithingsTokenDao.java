package io.myhealth.withings.dao;

import io.myhealth.withings.model.WithingsToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class WithingsTokenDao implements TokenDao {

    @Value("${myhealth.withings.tokenFile}")
    private String tokenFile;

    @Override
    public Mono<String> getAccessToken() {
        return WithingsToken
                .read(tokenFile)
                .map(WithingsToken::getAccessToken);
    }
}
