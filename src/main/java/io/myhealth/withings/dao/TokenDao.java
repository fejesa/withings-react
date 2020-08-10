package io.myhealth.withings.dao;

import reactor.core.publisher.Mono;

public interface TokenDao {

    Mono<String> getAccessToken();

}
