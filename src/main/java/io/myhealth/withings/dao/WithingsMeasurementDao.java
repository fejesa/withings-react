package io.myhealth.withings.dao;

import com.withings.api.heart.HeartList;
import com.withings.api.heart.Signal;
import com.withings.api.user.DeviceList;
import io.myhealth.withings.api.WithingsException;
import io.myhealth.withings.model.HeartsWithDevices;
import io.myhealth.withings.model.SignalWithDevices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

@Component
public class WithingsMeasurementDao implements MeasurementDao {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WebClient webClient;

    private final TokenDao tokenDao;

    public WithingsMeasurementDao(@Value("${myhealth.withings.baseUri}") String uri, TokenDao tokenDao) {
        this.tokenDao = tokenDao;
        this.webClient = WebClient.create(uri);
    }

    @Override
    public Mono<HeartsWithDevices> getHeartListAndDevices(WithingsHeartListRequest request) {
        var heartList = getHeartLists(request).subscribeOn(Schedulers.elastic());
        var deviceList = getDeviceList().subscribeOn(Schedulers.elastic());

        return Mono.zip(heartList, deviceList, HeartsWithDevices::new);
    }

    @Override
    public Mono<SignalWithDevices> getSignalAndDevices(WithingsSignalRequest request) {
        var signal = getSignal(request).subscribeOn(Schedulers.elastic());
        var deviceList = getDeviceList().subscribeOn(Schedulers.elastic());

        return Mono.zip(signal, deviceList, SignalWithDevices::new);
    }

    private Mono<Signal> getSignal(WithingsSignalRequest request) {
        return tokenDao.getAccessToken()
                .flatMap(rt -> webClient
                        .get()
                        .uri(uriBuilder -> buildHeartUri(uriBuilder, request))
                        .headers(h -> h.setBearerAuth(rt))
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new WithingsException("Client error during signal fetch")))
                        .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new WithingsException("Withings server error during signal fetch")))
                        .bodyToMono(Signal.class)
                        .doOnSuccess(t -> log.info("Signal {} is fetched", request.getSignalId()))
                        .doOnError(e -> log.error("Error during the signal fetch", e)));
    }

    private Mono<HeartList> getHeartLists(WithingsHeartListRequest request) {
        return tokenDao.getAccessToken()
                .flatMap(rt -> webClient
                        .get()
                        .uri(uriBuilder -> buildHeartListUri(uriBuilder, request))
                        .headers(h -> h.setBearerAuth(rt))
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new WithingsException("Client error during heart list fetch")))
                        .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new WithingsException("Withings server error during heart list fetch")))
                        .bodyToMono(HeartList.class)
                        .doOnSuccess(t -> log.info("Heart list is fetched from {} to {}", request.getFrom(), request.getTo()))
                        .doOnError(e -> log.error("Error during the heart list fetch", e)));
    }

    private Mono<DeviceList> getDeviceList() {
        return  tokenDao.getAccessToken()
                .flatMap(rt -> webClient
                        .get()
                        .uri(this::buildDeviceUri)
                        .headers(h -> h.setBearerAuth(rt))
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new WithingsException("Client error during device list fetch")))
                        .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new WithingsException("Withings server error during device list fetch")))
                        .bodyToMono(DeviceList.class)
                        .doOnSuccess(t -> log.info("Device list is fetched"))
                        .doOnError(e -> log.error("Error during the device list fetch", e)));
    }

    private URI buildDeviceUri(UriBuilder uriBuilder) {
        return uriBuilder.path("/user")
                .queryParam("action", "getdevice")
                .build();
    }

    private URI buildHeartUri(UriBuilder uriBuilder, WithingsSignalRequest request) {
        return uriBuilder.path("/heart")
                .queryParam("action", "get")
                .queryParam("signalid", request.getSignalId())
                .build();
    }

    private URI buildHeartListUri(UriBuilder uriBuilder, WithingsHeartListRequest request) {
        return uriBuilder.path("/heart")
                .queryParam("action", "list")
                .queryParam("startdate", toStartDate(request.getFrom()))
                .queryParam("enddate", toEndDate(request.getTo()))
                .queryParam("offset", request.getOffset())
                .build();
    }

    private long toStartDate(LocalDate date) {
        return date.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC);
    }
    private long toEndDate(LocalDate date) {
        return date.toEpochSecond(LocalTime.MAX, ZoneOffset.UTC);
    }
}
