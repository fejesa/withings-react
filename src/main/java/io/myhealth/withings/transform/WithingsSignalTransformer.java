package io.myhealth.withings.transform;

import com.withings.api.user.Device;
import com.withings.api.user.DeviceList;
import io.myhealth.withings.api.WithingsSignal;
import io.myhealth.withings.dao.SignalWithDevices;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class WithingsSignalTransformer implements Function<Mono<SignalWithDevices>, Mono<WithingsSignal>> {

    @Override
    public Mono<WithingsSignal> apply(Mono<SignalWithDevices> from) {
        return from.map(f -> new WithingsSignal(
                f.getSignal().getBody().getSignal(),
                f.getSignal().getBody().getSamplingFrequency(),
                findDevice(f.getDeviceList(), f.getSignal().getBody().getModel()),
                WearPosition.valueOf(f.getSignal().getBody().getWearPositionId()).getName()));
    }

    private String findDevice(DeviceList devices, int modelId) {
        return devices.getDeviceBody().getDevices()
                .stream()
                .filter(d -> d.getModelId() == modelId)
                .map(Device::getModel)
                .findAny()
                .orElse("Unknown");
    }
}