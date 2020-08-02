package io.myhealth.withings.dao;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MeasurementDao {

    Mono<HeartsWithDevices> getHeartListAndDevices(LocalDateTime from, LocalDateTime to);

    Mono<SignalWithDevices> getSignalAndDevices(int signalId);
}
