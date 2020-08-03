package io.myhealth.withings.api;

public class WithingsException extends RuntimeException {

    public WithingsException() {
        super();
    }

    public WithingsException(String message) {
        super(message);
    }

    public WithingsException(String message, Throwable cause) {
        super(message, cause);
    }
}
