package io.myhealth.withings.model;

import com.withings.api.heart.Signal;
import com.withings.api.user.DeviceList;

public class SignalWithDevices {

    private final Signal signal;

    private final DeviceList deviceList;

    public SignalWithDevices(Signal signal, DeviceList deviceList) {
        this.signal = signal;
        this.deviceList = deviceList;
    }

    public Signal getSignal() {
        return signal;
    }

    public DeviceList getDeviceList() {
        return deviceList;
    }

    public boolean isSuccess() {
        if (signal != null && deviceList != null) {
            return signal.getStatus() == 0 && deviceList.getStatus() == 0;
        }
        return false;
    }

    public String getStatus() {
        String signalStatus = signal != null ? Integer.toString(signal.getStatus()) : "Unknown";
        String deviceListStatus = deviceList != null ? Integer.toString(deviceList.getStatus()) : "Unknown";
        return String.format("%s,%s", signalStatus, deviceListStatus);
    }
}
