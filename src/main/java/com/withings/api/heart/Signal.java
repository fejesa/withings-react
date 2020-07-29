package com.withings.api.heart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Signal {

    private final int status;

    private final SignalBody body;

    @JsonCreator
    public Signal(@JsonProperty("status") int status, @JsonProperty("body") SignalBody body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public SignalBody getBody() {
        return body;
    }
}
