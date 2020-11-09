package io.myhealth.withings.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import io.myhealth.withings.dao.WithingsSignalRequest;
import io.myhealth.withings.dao.WithingsHeartListRequest;

public interface HeartService {

    /**
     * Provides list of ECG recordings and atrial fibrillation classification in the chosen period of time.
     * If the ECG recordings have been taken with BPM Core, the blood pressure is also provided.
     *
     * @param request Contains the period
     * @see WithingsHeartListRequest
     */
    Mono<ServerResponse> getHeartMeasurements(ServerRequest request);

    /**
     * Provides the full data set of the ECG recordings in micro-volt
     *
     * @param request Contains the id of the signal.
     * @see WithingsSignalRequest
     */
    Mono<ServerResponse> getEcgSignal(ServerRequest request);

    /**
     * Fetches blood pressure measurements in the given period.
     *
     * @param request Contains the period
     * @return List of blood pressure results.
     * @see WithingsHeartListRequest
     */
    Mono<ServerResponse> getBloodPressures(ServerRequest request);
}
