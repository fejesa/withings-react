package io.myhealth.withings.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WithingsSignalRequest {

    private final int signalId;

    @JsonCreator
    public WithingsSignalRequest(@JsonProperty("signalId") int signalId) {
        this.signalId = signalId;
    }

    public int getSignalId() {
        return signalId;
    }
}
