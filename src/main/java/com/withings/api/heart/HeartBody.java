package com.withings.api.heart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HeartBody {

    private final List<HeartMeasurement> series;

    private final boolean more;

    private final int offset;

    @JsonCreator
    public HeartBody(@JsonProperty("series") List<HeartMeasurement> series, @JsonProperty("more") boolean more, @JsonProperty("offset") int offset) {
        this.series = series;
        this.more = more;
        this.offset = offset;
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
}
