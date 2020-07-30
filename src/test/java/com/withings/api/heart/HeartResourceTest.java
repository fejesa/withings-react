package com.withings.api.heart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HeartResourceTest {

    @Test
    public void loadHeartList() throws Exception {
        URL resource = HeartResourceTest.class.getResource("/sample/heartlist.json");
        HeartList heartList = new ObjectMapper().readValue(resource, HeartList.class);
        assertNotNull(heartList);
    }

    @Test
    public void loadSignal() throws Exception {
        URL resource = HeartResourceTest.class.getResource("/sample/signal.json");
        Signal signal = new ObjectMapper().readValue(resource, Signal.class);
        assertNotNull(signal);
        assertEquals(10_000, signal.getBody().getSignal().length);
    }
}
