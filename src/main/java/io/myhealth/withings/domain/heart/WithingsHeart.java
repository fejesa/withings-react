package io.myhealth.withings.domain.heart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class WithingsHeart {

    private final int diastole;

    private final int systole;

    private final int heartRate;

    private final int signalId;

    private final LocalDateTime timestamp;

    @JsonCreator
    public WithingsHeart(@JsonProperty("diastole") int diastole, @JsonProperty("systole") int systole,
                         @JsonProperty("heartRate") int heartRate, @JsonProperty("signalId") int signalId,
                         @JsonProperty("timestamp") LocalDateTime timestamp) {
        this.diastole = diastole;
        this.systole = systole;
        this.heartRate = heartRate;
        this.signalId = signalId;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}