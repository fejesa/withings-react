package com.withings.api.heart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BloodPressure {

    private final int diastole;

    private final int systole;

    @JsonCreator
    public BloodPressure(@JsonProperty("diastole") int diastole, @JsonProperty("systole") int systole) {
        this.diastole = diastole;
        this.systole = systole;
    }

    public int getDiastole() {
        return diastole;
    }

    public int getSystole() {
        return systole;
    }
}