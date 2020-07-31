package io.myhealth.withings.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WithingsHeartResponse {

    private final String userId;

    private final List<WithingsHeart> heartList;

    @JsonCreator
    public WithingsHeartResponse(@JsonProperty("userId") String userId, @JsonProperty("heartList") List<WithingsHeart> heartList) {
        this.userId = userId;
        this.heartList = heartList;
    }

    public String getUserId() {
        return userId;
    }

    public List<WithingsHeart> getHeartList() {
        return heartList;
    }
}