package io.myhealth.withings.dao;

import io.myhealth.withings.model.HeartsWithDevices;
import io.myhealth.withings.model.SignalWithDevices;
import reactor.core.publisher.Mono;

public interface MeasurementDao {

    Mono<HeartsWithDevices> getHeartListAndDevices(WithingsHeartListRequest request);

    Mono<SignalWithDevices> getSignalAndDevices(WithingsSignalRequest request);
}
