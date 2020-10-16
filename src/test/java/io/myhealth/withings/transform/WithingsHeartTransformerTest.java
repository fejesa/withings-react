package io.myhealth.withings.transform;

import com.withings.api.heart.*;
import com.withings.api.user.Device;
import com.withings.api.user.DeviceBody;
import com.withings.api.user.DeviceList;
import io.myhealth.withings.api.WithingsHeartResponse;
import io.myhealth.withings.model.HeartsWithDevices;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WithingsHeartTransformerTest {

    @Test
    void transformEmpty() {
        HeartBody heartBody = new HeartBody(Collections.emptyList(), false, 0);
        HeartList heartList = new HeartList(0, heartBody);

        DeviceBody deviceBody = new DeviceBody(Collections.emptyList());
        DeviceList deviceList = new DeviceList(0, deviceBody);
        Mono<HeartsWithDevices> in = Mono.just(new HeartsWithDevices(heartList, deviceList));

        Mono<WithingsHeartResponse> out = new WithingsHeartTransformer().apply(in);
        out.subscribe(result -> assertTrue(result.getContent().isEmpty()));
    }


    @Test
    void transform() {
        int modelId = 1;

        HeartMeasurement measurement = new HeartMeasurement("device", modelId,
                new Ecg(1, 0),
                new BloodPressure(118, 75), 72, 1595881259);
        HeartList heartList = new HeartList(0, new HeartBody(List.of(measurement), false, 0));

        Device device = new Device("type", "battery", "model", modelId, "Europe/Berlin", 1, "deviceid");
        DeviceBody deviceBody = new DeviceBody(List.of(device));
        DeviceList deviceList = new DeviceList(0, deviceBody);

        Mono<HeartsWithDevices> in = Mono.just(new HeartsWithDevices(heartList, deviceList));

        Mono<WithingsHeartResponse> out = new WithingsHeartTransformer().apply(in);
        out.subscribe(result -> assertEquals(1, result.getContent().size()));
    }
}
