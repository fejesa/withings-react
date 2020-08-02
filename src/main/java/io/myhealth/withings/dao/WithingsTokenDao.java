package io.myhealth.withings.dao;

import org.springframework.stereotype.Component;

@Component
public class WithingsTokenDao implements TokenDao {

    @Override
    public String getAccessToken() {
        return "17d6b1af7cb513950bfa70e752094ff9940c6201";
    }

    @Override
    public String getRefreshToken() {
        return "";
    }
}
