package io.myhealth.withings.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WithingsSignal {

    private final int[] signal;

    private final int samplingFrequency;

    private final String deviceName;

    private final String wearPosition;

    @JsonCreator
    public WithingsSignal(@JsonProperty("signal") int[] signal, @JsonProperty("samplingFrequency") int samplingFrequency,
                          @JsonProperty("device") String deviceName, @JsonProperty("wearPosition") String wearPosition) {
        this.signal = signal;
        this.samplingFrequency = samplingFrequency;
        this.deviceName = deviceName;
        this.wearPosition = wearPosition;
    }

    public int[] getSignal() {
        return signal;
    }

    public int getSamplingFrequency() {
        return samplingFrequency;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getWearPosition() {
        return wearPosition;
    }
}