package com.withings.api.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeviceBody {

    private final List<Device> devices;

    @JsonCreator
    public DeviceBody(@JsonProperty("devices") List<Device> devices) {
        this.devices = devices;
    }

    public List<Device> getDevices() {
        return devices;
    }
}
