package com.withings.api.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Device {

    private final String type;

    private final String battery;

    private final String model;

    private final int modelId;

    private final String timezone;

    private final int lastSessionDate;

    private final String deviceId;

    @JsonCreator
    public Device(@JsonProperty("type") String type, @JsonProperty("battery") String battery, @JsonProperty("model") String model,
                  @JsonProperty("model_id") int modelId, @JsonProperty("timezone") String timezone,
                  @JsonProperty("last_session_date") int lastSessionDate, @JsonProperty("deviceid") String deviceId) {
        this.type = type;
        this.battery = battery;
        this.model = model;
        this.modelId = modelId;
        this.timezone = timezone;
        this.lastSessionDate = lastSessionDate;
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public String getBattery() {
        return battery;
    }

    public String getModel() {
        return model;
    }

    public int getModelId() {
        return modelId;
    }

    public String getTimezone() {
        return timezone;
    }

    public int getLastSessionDate() {
        return lastSessionDate;
    }

    public String getDeviceId() {
        return deviceId;
    }
}