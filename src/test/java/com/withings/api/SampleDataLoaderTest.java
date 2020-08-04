package com.withings.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.withings.api.heart.HeartList;
import com.withings.api.heart.Signal;
import com.withings.api.user.DeviceList;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class SampleDataLoaderTest {

    @Test
    void loadHeartList() throws Exception {
        URL resource = SampleDataLoaderTest.class.getResource("/sample/heartlist.json");
        HeartList heartList = new ObjectMapper().readValue(resource, HeartList.class);
        assertNotNull(heartList);
        assertFalse(heartList.getHeartBody().getSeries().isEmpty());
    }

    @Test
    void loadSignal() throws Exception {
        URL resource = SampleDataLoaderTest.class.getResource("/sample/signal.json");
        Signal signal = new ObjectMapper().readValue(resource, Signal.class);
        assertNotNull(signal);
        assertEquals(10_000, signal.getBody().getSignal().length);
    }

    @Test
    void loadDevices() throws Exception {
        URL resource = SampleDataLoaderTest.class.getResource("/sample/devices.json");
        DeviceList deviceList = new ObjectMapper().readValue(resource, DeviceList.class);
        assertNotNull(deviceList);
        assertFalse(deviceList.getDeviceBody().getDevices().isEmpty());

    }
}
