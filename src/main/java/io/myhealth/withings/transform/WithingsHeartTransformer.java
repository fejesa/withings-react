package io.myhealth.withings.transform;

import com.withings.api.heart.HeartList;
import com.withings.api.heart.HeartMeasurement;
import io.myhealth.withings.api.WithingsHeart;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class WithingsHeartTransformer implements Transformer<HeartList, List<WithingsHeart>> {

    @Override
    public List<WithingsHeart> transform(HeartList from) {
        return from.getHeartBody().getSeries()
                .stream()
                .map(this::fromMeasurement)
                .collect(Collectors.toList());
    }

    private WithingsHeart fromMeasurement(HeartMeasurement measurement) {
        int diastole = measurement.getBloodPressure().getDiastole();
        int systole = measurement.getBloodPressure().getSystole();
        int heartRate = measurement.getHeartRate();
        int signalId = measurement.getEcg().getSignalId();
        LocalDateTime timestamp = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(measurement.getTimestamp()), TimeZone.getDefault().toZoneId());
        return new WithingsHeart(diastole, systole, heartRate, signalId, timestamp);
    }
}
