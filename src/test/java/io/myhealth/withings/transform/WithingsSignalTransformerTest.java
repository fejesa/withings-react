package io.myhealth.withings.transform;

import io.myhealth.withings.api.WithingsSignal;
import io.myhealth.withings.model.SignalWithDevices;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WithingsSignalTransformerTest {

    @Test
    void transformEmpty() {
        Mono<SignalWithDevices> in = Mono.empty();
        Mono<WithingsSignal> out = new WithingsSignalTransformer().apply(in);
        out.subscribe(result -> assertTrue(result.getDeviceName().isEmpty()));
    }

//    @Test
//    void transform() {
//        int modelId = 1;
//        int wearPositionId = 0;
//        Device device = new Device("type", "battery", "model", modelId, "Europe/Berlin", 1, "deviceid");
//        DeviceBody deviceBody = new DeviceBody(List.of(device));
//        DeviceList deviceList = new DeviceList(0, deviceBody);
//
//        SignalBody signalBody = new SignalBody(new int[] {0}, 500, wearPositionId, modelId);
//        Signal signal = new Signal(0, signalBody);
//
//        Mono<SignalWithDevices> in = Mono.just(new SignalWithDevices(signal, deviceList));
//        Mono<WithingsSignal> out = new WithingsSignalTransformer().apply(in);
//        out.subscribe(result -> assertTrue(result.getSignal().length > 0));
//    }
}
