package io.myhealth.withings.transform;

import com.withings.api.heart.HeartMeasurement;
import com.withings.api.user.DeviceList;
import io.myhealth.withings.api.WithingsHeart;
import io.myhealth.withings.model.HeartsWithDevices;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WithingsHeartTransformer implements Function<Mono<HeartsWithDevices>, Mono<List<WithingsHeart>>> {

    @Override
    public Mono<List<WithingsHeart>> apply(Mono<HeartsWithDevices> from) {
        return from
                .map(this::transform)
                .defaultIfEmpty(Collections.emptyList());
    }

    private List<WithingsHeart> transform(HeartsWithDevices result) {
        return result.getHeartList()
                .getHeartBody().getSeries()
                .stream()
                .map(m -> fromMeasurement(m, result.getDeviceList()))
                .sorted(Comparator.comparing(WithingsHeart::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    private WithingsHeart fromMeasurement(HeartMeasurement measurement, DeviceList devices) {
        int diastole = measurement.getBloodPressure().getDiastole();
        int systole = measurement.getBloodPressure().getSystole();
        int heartRate = measurement.getHeartRate();
        int signalId = getSignalId(measurement);
        String deviceName = Devices.find(devices, measurement.getModelId());
        return new WithingsHeart(diastole, systole, heartRate, signalId, deviceName, measurement.getTimestamp());
    }

    private int getSignalId(HeartMeasurement measurement) {
        return measurement.getEcg() != null ? measurement.getEcg().getSignalId() : -1;
    }
}
