package io.myhealth.withings.transform;

import com.withings.api.heart.Signal;
import io.myhealth.withings.api.WithingsSignal;
import io.myhealth.withings.model.SignalWithDevices;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class WithingsSignalTransformer implements Function<Mono<SignalWithDevices>, Mono<WithingsSignal>> {

    @Override
    public Mono<WithingsSignal> apply(Mono<SignalWithDevices> from) {
        return from
                .map(this::transform)
                .defaultIfEmpty(WithingsSignal.empty());
    }

    private WithingsSignal transform(SignalWithDevices result) {
        return new WithingsSignal(
                result.getSignal().getSignal(),
                result.getSignal().getSamplingFrequency(),
                Devices.find(result.getDeviceList(), result.getSignal().getModel()),
                getWearPosition(result.getSignal()));
    }

    private String getWearPosition(Signal signal) {
        var wearPosition = signal.getSignal() == null ? -1 : signal.getWearPositionId();
        return WearPosition.valueOf(wearPosition).getName();
    }
}
