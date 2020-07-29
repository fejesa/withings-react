package com.withings.api.heart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ecg {

    private final int signalId;

    private final int afib;

    @JsonCreator
    public Ecg(@JsonProperty("signalid") int signalId, @JsonProperty("afib") int afib) {
        this.signalId = signalId;
        this.afib = afib;
    }

    public int getSignalId() {
        return signalId;
    }

    public int getAfib() {
        return afib;
    }
}
