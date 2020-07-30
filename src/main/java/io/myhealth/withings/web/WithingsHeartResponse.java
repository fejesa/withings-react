package io.myhealth.withings.web;

import io.myhealth.withings.domain.heart.WithingsHeart;

import java.util.List;

public class WithingsHeartResponse {

    private final String userId;

    private final List<WithingsHeart> heartList;

    public WithingsHeartResponse(String userId, List<WithingsHeart> heartList) {
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