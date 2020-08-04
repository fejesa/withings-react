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
import java.time.LocalDateTime;
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
    public Mono<HeartsWithDevices> getHeartListAndDevices(LocalDateTime from, LocalDateTime to) {
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
        return webClient
                .get()
                .uri(getHeartUri(signalId))
                .headers(h -> h.setBearerAuth(tokenDao.getAccessToken()))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new WithingsException("Client error")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new WithingsException("Server error")))
                .bodyToMono(Signal.class)
                .doOnSuccess(t -> log.info("Signal {} is fetched", signalId))
                .doOnError(e -> log.error("Error during the signal fetch", e));
    }

    private Mono<HeartList> getHeartLists(LocalDateTime from, LocalDateTime to) {
        return webClient
                .get()
                .uri(getHearListUri(from, to))
                .headers(h -> h.setBearerAuth(tokenDao.getAccessToken()))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new WithingsException("Client error")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new WithingsException("Server error")))
                .bodyToMono(HeartList.class)
                .doOnSuccess(t -> log.info("Heart list is fetched from {} to {}", from, to))
                .doOnError(e -> log.error("Error during the heart list fetch", e));
    }

    private Mono<DeviceList> getDeviceList() {
        return webClient
                .get()
                .uri("/user?action=getdevice")
                .headers(h -> h.setBearerAuth(tokenDao.getAccessToken()))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new WithingsException("Client error")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new WithingsException("Server error")))
                .bodyToMono(DeviceList.class)
                .doOnSuccess(t -> log.info("Device list is fetched"))
                .doOnError(e -> log.error("Error during the device list fetch", e));
    }

    private String getHeartUri(int signalId) {
        return "/heart?action=get&signalid=" + signalId;
    }

    private String getHearListUri(LocalDateTime from, LocalDateTime to) {
        StringBuilder builder = new StringBuilder("/heart?action=list");
        builder.append("&startdate=").append(toEpochSecond(from));
        builder.append("&enddate=").append(toEpochSecond(to));
        return builder.toString();
    }

    private long toEpochSecond(LocalDateTime time) {
        return time.toEpochSecond(ZoneOffset.UTC);
    }
}
