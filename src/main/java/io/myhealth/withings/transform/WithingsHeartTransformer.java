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
                .getSeries()
                .stream()
                .map(m -> fromMeasurement(m, result.getDeviceList()))
                .sorted(Comparator.comparing(WithingsHeart::getTimestamp).reversed())
                .collect(Collectors.toList());
        return new WithingsHeartResponse(hearts, offset(result));
    }

    private int offset(HeartsWithDevices result) {
        return result.getHeartList().getOffset();
    }

    private WithingsHeart fromMeasurement(HeartMeasurement measurement, DeviceList devices) {
        var deviceName = Devices.find(devices, measurement.getModel());
        return new WithingsHeart(measurement.getDiastole(), measurement.getSystole(),
                measurement.getHeartRate(), measurement.getSignalId(), deviceName, measurement.getTimestamp());
    }
}
