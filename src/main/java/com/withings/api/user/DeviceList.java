package com.withings.api.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.myhealth.withings.api.WithingsException;

import java.util.List;

public class DeviceList {

    private final int status;

    private final List<Device> devices;

    public DeviceList(int status, List<Device> devices) {
        this.status = status;
        this.devices = devices;
    }

    public int getStatus() {
        return status;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public static DeviceList fromString(String source) {
        try {
            var tree = new ObjectMapper().readTree(source);
            var status = tree.get("status").asInt();
            var devices = Device.fromString(source);
            return new DeviceList(status, devices);
        } catch (JsonProcessingException e) {
            throw new WithingsException("Cannot process device list", e);
        }
    }
}
