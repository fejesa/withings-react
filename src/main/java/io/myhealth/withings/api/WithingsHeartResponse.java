package io.myhealth.withings.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WithingsHeartResponse implements Page<WithingsHeart> {

    @JsonProperty("hearts")
    private final List<WithingsHeart> hearts;

    @JsonProperty("offset")
    private final int offset;

    @JsonProperty("pageSize")
    private final int pageSize;

    @JsonProperty("pageNumber")
    private final int pageNumber;

    public WithingsHeartResponse(List<WithingsHeart> hearts,
                                 int offset, int pageSize, int pageNumber) {
        this.hearts = hearts;
        this.offset = offset;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public WithingsHeartResponse(List<WithingsHeart> hearts, int offset) {
        this(hearts, offset, 0, 0);
    }

    @JsonIgnore
    @Override
    public List<WithingsHeart> getContent() {
        return this.hearts;
    }

    @Override
    public int getOffset() {
        return this.offset;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }
}
