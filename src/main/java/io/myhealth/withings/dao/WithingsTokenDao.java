package io.myhealth.withings.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.myhealth.withings.WithingsException;
import io.myhealth.withings.model.WithingsToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class WithingsTokenDao implements TokenDao {

    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${myhealth.withings.tokenFile}")
    private String tokenFile;

    @Override
    public String getAccessToken() {
        try {
            WithingsToken withingsToken = load();
            if (withingsToken.getAccessToken().isEmpty()) {
                throw new WithingsException("No Withings token found");
            }
            return withingsToken.getAccessToken();
        } catch (IOException e) {
            log.error("Token file loading failed, {}", e);
            throw new WithingsException("Token file loading failure");
        }
    }

    private WithingsToken load() throws IOException  {
        Path path = Paths.get(tokenFile).toAbsolutePath();
        return Files.exists(path) ? new ObjectMapper().readValue(path.toFile(), WithingsToken.class) : WithingsToken.empty();
    }
}