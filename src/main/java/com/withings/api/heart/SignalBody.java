package com.withings.api.heart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SignalBody {

    private int[] signal;

    private int samplingFrequency;

    private int wearPositionId;

    private int model;

    @JsonCreator
    public SignalBody(@JsonProperty("signal") int[] signal, @JsonProperty("sampling_frequency") int samplingFrequency,
                      @JsonProperty("wearposition") int wearPositionId, @JsonProperty("model") int model) {
        this.signal = signal;
        this.samplingFrequency = samplingFrequency;
        this.wearPositionId = wearPositionId;
        this.model = model;
    }

    public int[] getSignal() {
        return signal;
    }

    public int getSamplingFrequency() {
        return samplingFrequency;
    }

    public int getWearPositionId() {
        return wearPositionId;
    }

    public int getModel() {
        return model;
    }
}