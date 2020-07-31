package com.withings.api.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceList {

    private final int status;

    private final DeviceBody deviceBody;

    @JsonCreator
    public DeviceList(@JsonProperty("status") int status, @JsonProperty("body") DeviceBody deviceBody) {
        this.status = status;
        this.deviceBody = deviceBody;
    }

    public int getStatus() {
        return status;
    }

    public DeviceBody getDeviceBody() {
        return deviceBody;
    }
}
