package io.myhealth.withings.dao;

import io.myhealth.withings.model.HeartsWithDevices;
import io.myhealth.withings.model.SignalWithDevices;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MeasurementDao {

    // TODO: use int as params
    Mono<HeartsWithDevices> getHeartListAndDevices(LocalDateTime from, LocalDateTime to);

    Mono<SignalWithDevices> getSignalAndDevices(int signalId);
}
