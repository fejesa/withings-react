package io.myhealth.withings.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class WithingsRequest {

    private final String userId;

    private final LocalDateTime from;

    private final LocalDateTime to;

    @JsonCreator
    public WithingsRequest(@JsonProperty("userId") String userId, @JsonProperty("from") LocalDateTime from, @JsonProperty("to") LocalDateTime to) {
        this.userId = userId;
        this.from = from;
        this.to = to;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}
