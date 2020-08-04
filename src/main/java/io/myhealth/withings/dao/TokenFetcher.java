package io.myhealth.withings.dao;

public interface TokenFetcher {

    /**
     * Refreshes the Withings tokens.
     */
    void refreshToken();
}
