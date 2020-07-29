package com.withings.api.heart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SignalBody {

    private int[] signal;

    private int samplingFrequency;

    private int wearPosition;

    private int model;

    @JsonCreator
    public SignalBody(@JsonProperty("signal") int[] signal, @JsonProperty("sampling_frequency") int samplingFrequency,
                      @JsonProperty("wearposition") int wearPosition, @JsonProperty("model") int model) {
        this.signal = signal;
        this.samplingFrequency = samplingFrequency;
        this.wearPosition = wearPosition;
        this.model = model;
    }

    public int[] getSignal() {
        return signal;
    }

    public int getSamplingFrequency() {
        return samplingFrequency;
    }

    public int getWearPosition() {
        return wearPosition;
    }

    public int getModel() {
        return model;
    }
}