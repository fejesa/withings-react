package io.myhealth.withings.service;

import io.myhealth.withings.api.WithingsException;
import io.myhealth.withings.api.WithingsHeartResponse;
import io.myhealth.withings.dao.MeasurementDao;
import io.myhealth.withings.dao.WithingsHeartListRequest;
import io.myhealth.withings.dao.WithingsSignalRequest;
import io.myhealth.withings.transform.DateTimeTransformer;
import io.myhealth.withings.transform.WithingsHeartTransformer;
import io.myhealth.withings.transform.WithingsSignalTransformer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
public class WithingsHeartService implements HeartService {

    private static final int DEFAULT_PAGE_SIZE = 100;

    private final MeasurementDao measurementDao;

    public WithingsHeartService(MeasurementDao measurementDao) {
        this.measurementDao = measurementDao;
    }

    @Override
    public Mono<ServerResponse> getHeartMeasurements(ServerRequest request) {
        return heartRequest(request)
                .flatMap(measurementDao::getHeartListAndDevices)
                .flatMap(hd -> hd.isSuccess() ? Mono.just(hd) : Mono.error(new WithingsException("Client error with status: " + hd.getStatus())))
                .transform(new WithingsHeartTransformer())
                .map(response -> pageInfo(request, response))
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    private WithingsHeartResponse pageInfo(ServerRequest request, WithingsHeartResponse response) {
        return new WithingsHeartResponse(response.getContent(), response.getOffset(), DEFAULT_PAGE_SIZE, getPageNumber(request));
    }

    @Override
    public Mono<ServerResponse> getEcgSignal(ServerRequest request) {
        return signalRequest(request)
                .flatMap(measurementDao::getSignalAndDevices)
                .flatMap(sd -> sd.isSuccess() ? Mono.just(sd) : Mono.error(new WithingsException("Client error with status: " + sd.getStatus())))
                .transform(new WithingsSignalTransformer())
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    private Mono<WithingsHeartListRequest> heartRequest(ServerRequest request) {
        var from = request.queryParam("from")
                .map(DateTimeTransformer::fromString)
                .orElseGet(() -> LocalDate.now().minusWeeks(1));
        var to = request.queryParam("to")
                .map(DateTimeTransformer::fromString)
                .orElseGet(LocalDate::now);
        var offset = request.queryParam("offset")
                .map(Integer::parseInt)
                .orElse(0);
        return Mono.just(new WithingsHeartListRequest(from, to, offset));
    }

    private int getPageNumber(ServerRequest request) {
        return request.queryParam("page")
                .map(Integer::parseInt)
                .orElse(0);
    }

    private Mono<WithingsSignalRequest> signalRequest(ServerRequest request) {
        return request.queryParam("signalId")
                .map(Integer::parseInt)
                .map(WithingsSignalRequest::new)
                .map(Mono::just)
                .orElseThrow(WithingsException::new);
    }
}
