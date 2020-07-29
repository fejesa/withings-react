package com.withings.api.heart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HeartList {

    private final int status;

    private final HeartBody heartBody;

    @JsonCreator
    public HeartList(@JsonProperty("status") int status, @JsonProperty("body") HeartBody heartBody) {
        this.status = status;
        this.heartBody = heartBody;
    }

    public int getStatus() {
        return status;
    }

    public HeartBody getHeartBody() {
        return heartBody;
    }
}
