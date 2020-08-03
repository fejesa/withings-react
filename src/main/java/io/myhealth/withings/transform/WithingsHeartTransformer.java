package io.myhealth.withings.transform;

import com.withings.api.heart.HeartMeasurement;
import com.withings.api.user.Device;
import com.withings.api.user.DeviceList;
import io.myhealth.withings.api.WithingsHeart;
import io.myhealth.withings.model.HeartsWithDevices;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WithingsHeartTransformer implements Function<Mono<HeartsWithDevices>, Mono<List<WithingsHeart>>> {

    @Override
    public Mono<List<WithingsHeart>> apply(Mono<HeartsWithDevices> from) {
        return from.map(f -> f
                .getHeartList()
                .getHeartBody().getSeries()
                    .stream()
                    .map(m -> fromMeasurement(m, f.getDeviceList()))
                .sorted(Comparator.comparing(WithingsHeart::getTimestamp).reversed())
                .collect(Collectors.toList()));
    }

    private WithingsHeart fromMeasurement(HeartMeasurement measurement, DeviceList devices) {
        int diastole = measurement.getBloodPressure().getDiastole();
        int systole = measurement.getBloodPressure().getSystole();
        int heartRate = measurement.getHeartRate();
        int signalId = getSignalId(measurement);
        String deviceName = findDevice(devices, measurement.getModelId());
        LocalDateTime timestamp = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(measurement.getTimestamp()), TimeZone.getDefault().toZoneId());

        return new WithingsHeart(diastole, systole, heartRate, signalId, deviceName, timestamp);
    }

    private String findDevice(DeviceList devices, int modelId) {
        return devices.getDeviceBody().getDevices()
                .stream()
                .filter(d -> d.getModelId() == modelId)
                .map(Device::getModel)
                .findAny()
                .orElse("Unknown");
    }

    private int getSignalId(HeartMeasurement measurement) {
        return measurement.getEcg() != null ? measurement.getEcg().getSignalId() : -1;
    }
}