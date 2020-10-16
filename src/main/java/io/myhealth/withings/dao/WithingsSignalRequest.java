package io.myhealth.withings.dao;

public class WithingsSignalRequest {

    private final int signalId;

    public WithingsSignalRequest(int signalId) {
        this.signalId = signalId;
    }

    public int getSignalId() {
        return signalId;
    }
}
