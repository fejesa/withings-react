package com.withings.api.heart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.myhealth.withings.api.WithingsException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class HeartMeasurement {

    private final int signalId;

    private final int afib;

    private final int diastole;

    private final int systole;

    private final int heartRate;

    private final int timestamp;

    private final int model;

    public HeartMeasurement(int signalId, int afib, int diastole, int systole, int heartRate, int model, int timestamp) {
        this.signalId = signalId;
        this.afib = afib;
        this.diastole = diastole;
        this.systole = systole;
        this.heartRate = heartRate;
        this.model = model;
        this.timestamp = timestamp;
    }

    public int getSignalId() {
        return signalId;
    }

    public int getAfib() {
        return afib;
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

    public int getModel() {
        return model;
    }

    public static List<HeartMeasurement> fromString(String source) {
        try {
            var tree = new ObjectMapper().readTree(source);
            var series = tree.get("body").get("series").elements();

             BiFunction<JsonNode, String, Integer> getBloodPressure = (node, field) -> node.has("bloodpressure") ? node.get("bloodpressure").get(field).asInt() : -1;

            var result = new ArrayList<HeartMeasurement>();
            while (series.hasNext()) {
                var node = series.next();

                var signalId = node.get("ecg").get("signalid").asInt();
                var afib = node.get("ecg").get("afib").asInt();
                var diastole = getBloodPressure.apply(node, "diastole");
                var systole = getBloodPressure.apply(node,"systole");
                var heartRate = node.get("heart_rate").asInt();
                var model = node.get("model").asInt();
                var timestamp = node.get("timestamp").asInt();

                result.add(new HeartMeasurement(signalId, afib, diastole, systole, heartRate, model, timestamp));
            }
            return result;
        } catch (JsonProcessingException e) {
            throw new WithingsException("Cannot process heart measurement", e);
        }
    }
}
