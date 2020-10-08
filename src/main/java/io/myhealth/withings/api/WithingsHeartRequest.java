package io.myhealth.withings.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class WithingsHeartRequest {

    private final LocalDate from;

    private final LocalDate to;

    @JsonCreator
    public WithingsHeartRequest(@JsonProperty("from") LocalDate from, @JsonProperty("to") LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}
