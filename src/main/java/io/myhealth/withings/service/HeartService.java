package io.myhealth.withings.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface HeartService {

    Mono<ServerResponse> getHeartMeasurements(ServerRequest request);

    Mono<ServerResponse> getEcgSignal(ServerRequest request);
}
