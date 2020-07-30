package io.myhealth.withings;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class WithingsHeartHandler {

    public Mono<ServerResponse> getHeartMeasurement(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> getHeartMeasurementStream(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> getEcgSignal(ServerRequest request) {
        return null;
    }
}
