package io.myhealth.withings.transform;

import com.withings.api.heart.HeartMeasurement;
import com.withings.api.user.DeviceList;
import io.myhealth.withings.api.WithingsHeart;
import io.myhealth.withings.api.WithingsHeartResponse;
import io.myhealth.withings.model.HeartsWithDevices;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WithingsHeartTransformer implements Function<Mono<HeartsWithDevices>, Mono<WithingsHeartResponse>> {

    @Override
    public Mono<WithingsHeartResponse> apply(Mono<HeartsWithDevices> from) {
        return from
                .map(this::transform);
    }

    private WithingsHeartResponse transform(HeartsWithDevices result) {
        var hearts = result.getHeartList()
                .getHeartBody().getSeries()
                .stream()
                .map(m -> fromMeasurement(m, result.getDeviceList()))
                .sorted(Comparator.comparing(WithingsHeart::getTimestamp).reversed())
                .collect(Collectors.toList());
        return new WithingsHeartResponse(hearts, offset(result));
    }

    private int offset(HeartsWithDevices result) {
        return result.getHeartList().getHeartBody().getOffset();
    }

    private WithingsHeart fromMeasurement(HeartMeasurement measurement, DeviceList devices) {
        var diastole = measurement.getBloodPressure().getDiastole();
        var systole = measurement.getBloodPressure().getSystole();
        var heartRate = measurement.getHeartRate();
        var signalId = getSignalId(measurement);
        var deviceName = Devices.find(devices, measurement.getModelId());
        return new WithingsHeart(diastole, systole, heartRate, signalId, deviceName, measurement.getTimestamp());
    }

    private int getSignalId(HeartMeasurement measurement) {
        return measurement.getEcg() != null ? measurement.getEcg().getSignalId() : -1;
    }
}
