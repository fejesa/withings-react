package io.myhealth.withings.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class WithingsHeartRequest {

    private final LocalDateTime from;

    private final LocalDateTime to;

    @JsonCreator
    public WithingsHeartRequest(@JsonProperty("from") LocalDateTime from, @JsonProperty("to") LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}