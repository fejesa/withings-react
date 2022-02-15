package com.withings.api.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.myhealth.withings.api.WithingsException;

import java.util.ArrayList;
import java.util.List;

public class Device {

    private final String type;

    private final int modelId;

    private final String model;

    public Device(String type, String model, int modelId) {
        this.type = type;
        this.model = model;
        this.modelId = modelId;
    }

    public int getModelId() {
        return modelId;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public static List<Device> fromString(String source) {
        try {
            var tree = new ObjectMapper().readTree(source);
            var devices = tree.get("body").get("devices").elements();
            var result = new ArrayList<Device>();
            while (devices.hasNext()) {
                var device = devices.next();
                var type = device.get("type").asText();
                var model = device.get("model").asText();
                var modelId = device.get("model_id").asInt();
                result.add(new Device(type, model, modelId));
            }

            return result;
        } catch (JsonProcessingException e) {
            throw new WithingsException("Cannot process device list", e);
        }
    }
}
