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
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.lang.invoke.MethodHandles;
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
    public Mono<HeartsWithDevices> getHeartListAndDevices(LocalDate from, LocalDate to) {
        Mono<HeartList> heartList = getHeartLists(from, to).subscribeOn(Schedulers.elastic());
        Mono<DeviceList> deviceList = getDeviceList().subscribeOn(Schedulers.elastic());

        return Mono.zip(heartList, deviceList, HeartsWithDevices::new);
    }

    @Override
    public Mono<SignalWithDevices> getSignalAndDevices(int signalId) {
        Mono<Signal> signal = getSignal(signalId).subscribeOn(Schedulers.elastic());
        Mono<DeviceList> deviceList = getDeviceList().subscribeOn(Schedulers.elastic());

        return Mono.zip(signal, deviceList, SignalWithDevices::new);
    }

    private Mono<Signal> getSignal(int signalId) {
        return tokenDao.getAccessToken()
                .flatMap(rt -> webClient
                        .get()
                        .uri(getHeartUri(signalId))
                        .headers(h -> h.setBearerAuth(rt))
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new WithingsException("Client error during signal fetch")))
                        .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new WithingsException("Withings server error during signal fetch")))
                        .bodyToMono(Signal.class)
                        .doOnSuccess(t -> log.info("Signal {} is fetched", signalId))
                        .doOnError(e -> log.error("Error during the signal fetch", e)));
    }

    private Mono<HeartList> getHeartLists(LocalDate from, LocalDate to) {
        return tokenDao.getAccessToken()
                .flatMap(rt -> webClient
                        .get()
                        .uri(getHearListUri(from, to))
                        .headers(h -> h.setBearerAuth(rt))
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new WithingsException("Client error during heart list fetch")))
                        .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new WithingsException("Withings server error during heart list fetch")))
                        .bodyToMono(HeartList.class)
                        .doOnSuccess(t -> log.info("Heart list is fetched from {} to {}", from, to))
                        .doOnError(e -> log.error("Error during the heart list fetch", e)));
    }

    private Mono<DeviceList> getDeviceList() {
        return  tokenDao.getAccessToken()
                .flatMap(rt -> webClient
                        .get()
                        .uri("/user?action=getdevice")
                        .headers(h -> h.setBearerAuth(rt))
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new WithingsException("Client error during device list fetch")))
                        .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new WithingsException("Withings server error during device list fetch")))
                        .bodyToMono(DeviceList.class)
                        .doOnSuccess(t -> log.info("Device list is fetched"))
                        .doOnError(e -> log.error("Error during the device list fetch", e)));
    }

    private String getHeartUri(int signalId) {
        return "/heart?action=get&signalid=" + signalId;
    }

    private String getHearListUri(LocalDate from, LocalDate to) {
        StringBuilder builder = new StringBuilder("/heart?action=list");
        builder.append("&startdate=").append(toStartDate(from));
        builder.append("&enddate=").append(toEndDate(to));
        return builder.toString();
    }

    private long toStartDate(LocalDate date) {
        return date.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC);
    }
    private long toEndDate(LocalDate date) {
        return date.toEpochSecond(LocalTime.MAX, ZoneOffset.UTC);
    }
}
