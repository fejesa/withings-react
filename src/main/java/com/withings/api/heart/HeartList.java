package com.withings.api.heart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.myhealth.withings.api.WithingsException;

import java.util.List;

public class HeartList {

    private final int status;

    private final List<HeartMeasurement> series;

    private final boolean more;

    private final int offset;

    public HeartList(int status, List<HeartMeasurement> series, boolean more, int offset) {
        this.status = status;
        this.series = series;
        this.more = more;
        this.offset = offset;
    }

    public int getStatus() {
        return status;
    }

    public List<HeartMeasurement> getSeries() {
        return series;
    }

    public boolean isMore() {
        return more;
    }

    public int getOffset() {
        return offset;
    }

    public static HeartList fromString(String source) {
        try {
            var tree = new ObjectMapper().readTree(source);

            var status = tree.get("status").asInt();
            var hasMore = tree.get("body").get("more").asBoolean();
            var offset = tree.get("body").get("offset").asInt();
            var series = HeartMeasurement.fromString(source);

            return new HeartList(status, series, hasMore, offset);
        } catch (JsonProcessingException e) {
            throw new WithingsException("Cannot process heart list", e);
        }
    }
}
