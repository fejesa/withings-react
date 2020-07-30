package com.withings.api.heart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HeartMeasurement {

    private final String deviceId;

    private final int model;

    private final Ecg ecg;

    private final BloodPressure bloodPressure;

    private final int heartRate;

    private final int timestamp;

    @JsonCreator
    public HeartMeasurement(@JsonProperty("deviceid") String deviceId, @JsonProperty("model") int model,
                            @JsonProperty("ecg") Ecg ecg, @JsonProperty("bloodpressure") BloodPressure bloodPressure,
                            @JsonProperty("heart_rate") int heartRate, @JsonProperty("timestamp") int timestamp) {
        this.deviceId = deviceId;
        this.model = model;
        this.ecg = ecg;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public int getModel() {
        return model;
    }

    public Ecg getEcg() {
        return ecg;
    }

    public BloodPressure getBloodPressure() {
        return bloodPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getTimestamp() {
        return timestamp;
    }
}
