package com.withings.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.withings.api.heart.HeartMeasurement;
import com.withings.api.heart.Signal;
import com.withings.api.user.DeviceList;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class SampleDataLoaderTest {

    @Test
    void loadSignal() throws Exception {
        URL resource = SampleDataLoaderTest.class.getResource("/sample/signal.json");
        var source = Files.readString(Paths.get(resource.toURI()));
        var signal = Signal.fromString(source);
        assertNotNull(signal);
        assertEquals(10_000, signal.getSignal().length);
    }

    @Test
    void loadDevices() throws Exception {
        URL resource = SampleDataLoaderTest.class.getResource("/sample/devices.json");
        DeviceList deviceList = new ObjectMapper().readValue(resource, DeviceList.class);
        assertNotNull(deviceList);
        assertFalse(deviceList.getDevices().isEmpty());

    }

    @Test
    void loadMeasurements() throws Exception {
        var resource = SampleDataLoaderTest.class.getResource("/sample/heartlist.json");
        var source = Files.readString(Paths.get(resource.toURI()));
        var result = HeartMeasurement.fromString(source);
        assertNotNull(result);
        assertTrue(!result.isEmpty());
    }
}
