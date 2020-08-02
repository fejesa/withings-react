package io.myhealth.withings.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class WithingsHeartRequest {

    private final int from;

    private final int to;

    @JsonCreator
    public WithingsHeartRequest(@JsonProperty("from") int from, @JsonProperty("to") int to) {
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return toDateTime(from);
    }

    public LocalDateTime getTo() {
        return toDateTime(to);
    }

    private LocalDateTime toDateTime(int i) {
        return LocalDateTime.ofEpochSecond(i, 0, ZoneOffset.UTC);
    }
}