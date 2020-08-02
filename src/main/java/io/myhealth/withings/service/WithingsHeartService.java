package io.myhealth.withings.service;

import io.myhealth.withings.api.WithingsHeartRequest;
import io.myhealth.withings.api.WithingsSignalRequest;
import io.myhealth.withings.dao.MeasurementDao;
import io.myhealth.withings.transform.WithingsHeartTransformer;
import io.myhealth.withings.transform.WithingsSignalTransformer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class WithingsHeartService implements HeartService {

    private final MeasurementDao measurementDao;

    public WithingsHeartService(MeasurementDao measurementDao) {
        this.measurementDao = measurementDao;
    }

    @Override
    public Mono<ServerResponse> getHeartMeasurements(ServerRequest request) {
        return request.bodyToMono(WithingsHeartRequest.class)
                .flatMap(r -> measurementDao.getHeartListAndDevices(r.getFrom(), r.getTo()))
                .transform(new WithingsHeartTransformer())
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    @Override
    public Mono<ServerResponse> getEcgSignal(ServerRequest request) {
        return request.bodyToMono(WithingsSignalRequest.class)
                .flatMap(r -> measurementDao.getSignalAndDevices(r.getSignalId()))
                .transform(new WithingsSignalTransformer())
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.badRequest().build());
    }
}
