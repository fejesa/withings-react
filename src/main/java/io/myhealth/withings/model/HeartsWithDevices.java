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

    public boolean isSuccess() {
        if (heartList != null && deviceList != null) {
            return heartList.getStatus() == 0 && deviceList.getStatus() == 0;
        }
        return false;
    }

    public String getStatus() {
        String heartListStatus = heartList != null ? Integer.toString(heartList.getStatus()) : "Unknown";
        String deviceListStatus = deviceList != null ? Integer.toString(deviceList.getStatus()) : "Unknown";
        return String.format("%s,%s", heartListStatus, deviceListStatus);
    }
}
