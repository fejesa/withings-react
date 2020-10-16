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
    private int size;

    @JsonProperty("pageNumber")
    private int pageNumber;

    public WithingsHeartResponse(List<WithingsHeart> hearts,
                                 int offset) {
        this.hearts = hearts;
        this.offset = offset;
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
        return this.size;
    }

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
