package io.myhealth.withings.service;

import com.withings.api.heart.HeartList;
import com.withings.api.heart.HeartMeasurement;
import io.myhealth.withings.api.WithingsException;
import io.myhealth.withings.api.WithingsHeartResponse;
import io.myhealth.withings.dao.MeasurementDao;
import io.myhealth.withings.dao.WithingsHeartListRequest;
import io.myhealth.withings.dao.WithingsSignalRequest;
import io.myhealth.withings.transform.DateTimeTransformer;
import io.myhealth.withings.transform.WithingsBloodPressureTransformer;
import io.myhealth.withings.transform.WithingsHeartTransformer;
import io.myhealth.withings.transform.WithingsSignalTransformer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class WithingsHeartService implements HeartService {

    private static final int DEFAULT_PAGE_SIZE = 100;

    private final MeasurementDao measurementDao;

    public WithingsHeartService(MeasurementDao measurementDao) {
        this.measurementDao = measurementDao;
    }

    @Override
    public Mono<ServerResponse> getBloodPressures(ServerRequest request) {
        var heartRequest = heartRequest(request);

        return Mono.just(heartRequest)
                .flatMap(measurementDao::getBloodPressures)
                .flatMap(this::mapByStatus)
                .flatMap(r -> {
                    Mono<List<HeartMeasurement>> first = Mono.just(r.getHeartBody().getSeries());
                    Mono<List<HeartMeasurement>> second = r.getHeartBody().getOffset() > 0 ?
                            Mono.just(new WithingsHeartListRequest(heartRequest.getFrom(), heartRequest.getTo(), r.getHeartBody().getOffset()))
                                    .flatMap(measurementDao::getBloodPressures)
                                    .flatMap(this::mapByStatus)
                                    .flatMap(hr -> Mono.just(hr.getHeartBody().getSeries())) : Mono.just(Collections.emptyList());
                    return Mono.zip(first, second);
                    })
                .map(t -> Stream.of(t.getT1(), t.getT2())
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList()))
                .transform(new WithingsBloodPressureTransformer())
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    private Mono<HeartList> mapByStatus(HeartList heartList) {
        return heartList.getStatus() == 0 ? Mono.just(heartList) : Mono.error(new WithingsException("Client error with status: " + heartList.getStatus()));
    }

    @Override
    public Mono<ServerResponse> getHeartMeasurements(ServerRequest request) {
        return Mono.just(heartRequest(request))
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

    private WithingsHeartListRequest heartRequest(ServerRequest request) {
        var from = request.queryParam("from")
                .map(DateTimeTransformer::fromString)
                .orElseGet(() -> LocalDate.now().minusWeeks(1));
        var to = request.queryParam("to")
                .map(DateTimeTransformer::fromString)
                .orElseGet(LocalDate::now);
        var offset = request.queryParam("offset")
                .map(Integer::parseInt)
                .orElse(0);
        return new WithingsHeartListRequest(from, to, offset);
    }

    private int getPageNumber(ServerRequest request) {
        return request.queryParam("page")
                .map(Integer::parseInt)
                .orElse(0);
    }

    private Mono<WithingsSignalRequest> signalRequest(ServerRequest request) {
        return request.queryParam("signalid")
                .map(Integer::parseInt)
                .map(WithingsSignalRequest::new)
                .map(Mono::just)
                .orElseThrow(WithingsException::new);
    }
}
