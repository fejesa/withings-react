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
}