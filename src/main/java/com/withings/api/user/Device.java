package com.withings.api.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.myhealth.withings.api.WithingsException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public static List<Device> fromJson(@NotNull String source) {
        try {
            var tree = new ObjectMapper().readTree(source);
            var devices = tree.get("body").get("devices");
            return StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(devices.elements(), Spliterator.ORDERED), false)
                    .map(Device::getDevice)
                    .collect(Collectors.toList());

        } catch (JsonProcessingException e) {
            throw new WithingsException("Cannot process device list", e);
        }
    }

    private static Device getDevice(JsonNode node) {
        var type = node.get("type").asText();
        var model = node.get("model").asText();
        var modelId = node.get("model_id").asInt();

        return new Device(type, model, modelId);
    }
}
