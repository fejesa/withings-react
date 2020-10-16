package io.myhealth.withings.transform;

import com.withings.api.heart.HeartMeasurement;
import com.withings.api.user.DeviceList;
import io.myhealth.withings.api.WithingsHeart;
import io.myhealth.withings.api.WithingsHeartResponse;
import io.myhealth.withings.model.HeartsWithDevices;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WithingsHeartTransformer implements Function<Mono<HeartsWithDevices>, Mono<WithingsHeartResponse>> {

    @Override
    public Mono<WithingsHeartResponse> apply(Mono<HeartsWithDevices> from) {
        var hearts = from
                .map(this::transform)
                .defaultIfEmpty(Collections.emptyList());
        var offset = from.map(this::offset);

        return Mono.zip(hearts, offset).map(this::createResponse);
    }

    private WithingsHeartResponse createResponse(Tuple2<List<WithingsHeart>, Integer> tuple) {
        return new WithingsHeartResponse(tuple.getT1(), tuple.getT2());
    }

    private int offset(HeartsWithDevices result) {
        return result.getHeartList().getHeartBody().getOffset();
    }

    private List<WithingsHeart> transform(HeartsWithDevices result) {
        System.out.println("More result: " + result.getHeartList().getHeartBody().isMore());
        System.out.println("Offset: " + result.getHeartList().getHeartBody().getOffset());
        List<WithingsHeart> list = result.getHeartList()
                .getHeartBody().getSeries()
                .stream()
                .map(m -> fromMeasurement(m, result.getDeviceList()))
                .sorted(Comparator.comparing(WithingsHeart::getTimestamp).reversed())
                .collect(Collectors.toList());
        return list;
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
