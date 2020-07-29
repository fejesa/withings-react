package com.withings.api.heart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HeartList {

    private final int status;

    private final Body body;

    @JsonCreator
    public HeartList(@JsonProperty("status") int status, @JsonProperty("body") Body body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public Body getBody() {
        return body;
    }
}
