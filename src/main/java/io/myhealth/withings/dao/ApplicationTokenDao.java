package io.myhealth.withings.dao;

import io.myhealth.withings.model.ApplicationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ApplicationTokenDao implements TokenDao {

    @Value("${myhealth.withings.tokenFile}")
    private String tokenFile;

    @Override
    public Mono<String> getAccessToken() {
        return ApplicationToken
                .read(tokenFile)
                .map(ApplicationToken::getAccessToken);
    }
}
