package io.myhealth.withings.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class WithingsHeart {

    private final int diastole;

    private final int systole;

    private final int heartRate;

    /** -1 means no signal is detected */
    private final int signalId;

    private int afib;

    private final String deviceName;

    private final int timestamp;

    @JsonCreator
    public WithingsHeart(@JsonProperty("diastole") int diastole, @JsonProperty("systole") int systole,
                         @JsonProperty("heartRate") int heartRate, @JsonProperty("signalId") int signalId,
                         @JsonProperty("deviceName") String deviceName, @JsonProperty("timestamp") int timestamp) {
        this.diastole = diastole;
        this.systole = systole;
        this.heartRate = heartRate;
        this.signalId = signalId;
        this.deviceName = deviceName;
        this.timestamp = timestamp;
    }

    public int getDiastole() {
        return diastole;
    }

    public int getSystole() {
        return systole;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getSignalId() {
        return signalId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public static List<WithingsHeart> fromString(String source) {
        try {
            var node = new ObjectMapper().readTree(source);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
