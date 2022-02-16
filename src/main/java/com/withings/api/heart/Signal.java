package com.withings.api.heart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import io.myhealth.withings.api.WithingsException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class Signal {

    private final int status;

    private final int[] signal;

    private final int samplingFrequency;

    private final int wearPositionId;

    private final int model;

    public Signal(int status, int[] signal, int samplingFrequency, int wearPositionId, int model) {
        this.status = status;
        this.signal = signal;
        this.samplingFrequency = samplingFrequency;
        this.wearPositionId = wearPositionId;
        this.model = model;
    }

    public int getStatus() {
        return status;
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

    public static Signal fromJson(@NotNull String source) {
        try {
            var tree = new ObjectMapper().readTree(source);

            var status = tree.get("status").asInt();
            var body = tree.get("body");

            var frequency = body.get("sampling_frequency").asInt();
            var wearPosition = body.get("wearposition").asInt();
            var model = body.get("model").asInt();

            var signal = (ArrayNode) body.get("signal");
            var array = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(signal.elements(), Spliterator.ORDERED), false)
                    .map(n -> (IntNode) n)
                    .mapToInt(i -> i.asInt())
                    .toArray();

            return new Signal(status, array, frequency, wearPosition, model);

        } catch (IOException e) {
            throw new WithingsException("Cannot process signal", e);
        }
    }
}
