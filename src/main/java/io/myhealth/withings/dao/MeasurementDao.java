package io.myhealth.withings.dao;

import io.myhealth.withings.model.HeartsWithDevices;
import io.myhealth.withings.model.SignalWithDevices;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface MeasurementDao {

    Mono<HeartsWithDevices> getHeartListAndDevices(LocalDate from, LocalDate to);

    Mono<SignalWithDevices> getSignalAndDevices(int signalId);
}
