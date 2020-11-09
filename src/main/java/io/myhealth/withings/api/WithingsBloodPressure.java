package io.myhealth.withings.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WithingsBloodPressure {

    private final int diastole;

    private final int systole;

    private final int heartRate;

    private final int timestamp;

    @JsonCreator
    public WithingsBloodPressure(@JsonProperty("diastole") int diastole, @JsonProperty("systole") int systole,
                                 @JsonProperty("heartRate") int heartRate, @JsonProperty("timestamp") int timestamp) {
        this.diastole = diastole;
        this.systole = systole;
        this.heartRate = heartRate;
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

    public int getTimestamp() {
        return timestamp;
    }
}
