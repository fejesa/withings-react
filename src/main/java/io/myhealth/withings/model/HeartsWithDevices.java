package io.myhealth.withings.model;

import com.withings.api.heart.HeartList;
import com.withings.api.user.DeviceList;

public class HeartsWithDevices {

    private final HeartList heartList;

    private final DeviceList deviceList;

    public HeartsWithDevices(HeartList heartList, DeviceList deviceList) {
        this.heartList = heartList;
        this.deviceList = deviceList;
    }

    public HeartList getHeartList() {
        return heartList;
    }

    public DeviceList getDeviceList() {
        return deviceList;
    }
}