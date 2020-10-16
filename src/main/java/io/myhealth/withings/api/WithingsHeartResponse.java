package io.myhealth.withings.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WithingsHeartResponse implements Page<WithingsHeart> {

    @JsonProperty("content")
    private final List<WithingsHeart> content;

    @JsonProperty("offset")
    private final int offset;

    @JsonProperty("pageSize")
    private int size;

    @JsonProperty("pageNumber")
    private int pageNumber;

    public WithingsHeartResponse(List<WithingsHeart> content,
                                 int offset) {
        this.content = content;
        this.offset = offset;
    }

    @Override
    public List<WithingsHeart> getContent() {
        return this.content;
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
