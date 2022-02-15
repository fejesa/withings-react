package io.myhealth.withings.transform;

import com.withings.api.heart.HeartMeasurement;
import io.myhealth.withings.api.WithingsBloodPressure;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WithingsBloodPressureTransformer implements Function<Mono<List<HeartMeasurement>>, Mono<List<WithingsBloodPressure>>>  {

    @Override
    public Mono<List<WithingsBloodPressure>> apply(Mono<List<HeartMeasurement>> from) {
        return from.map(this::transform);
    }

    private List<WithingsBloodPressure> transform(List<HeartMeasurement> list) {
        return list
                .stream()
                .map(this::fromMeasurement)
                .sorted(Comparator.comparing(WithingsBloodPressure::getTimestamp))
                .collect(Collectors.toList());
    }

    private WithingsBloodPressure fromMeasurement(HeartMeasurement measurement) {
        return new WithingsBloodPressure(measurement.getDiastole(), measurement.getSystole(), measurement.getHeartRate(), measurement.getTimestamp());
    }
}
