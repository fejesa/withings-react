package com.withings.api.heart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Body {

    private final List<Series> series;

    private final boolean more;

    private final int offset;

    @JsonCreator
    public Body(@JsonProperty("series") List<Series> series, @JsonProperty("more") boolean more, @JsonProperty("offset") int offset) {
        this.series = series;
        this.more = more;
        this.offset = offset;
    }

    public List<Series> getSeries() {
        return series;
    }

    public boolean isMore() {
        return more;
    }

    public int getOffset() {
        return offset;
    }
}
