package io.myhealth.withings.dao;

import java.time.LocalDate;

public class WithingsHeartListRequest {

    private final LocalDate from;

    private final LocalDate to;

    private final int offset;

    public WithingsHeartListRequest(LocalDate from, LocalDate to, int offset) {
        this.from = from;
        this.to = to;
        this.offset = offset;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public int getOffset() {
        return offset;
    }
}
