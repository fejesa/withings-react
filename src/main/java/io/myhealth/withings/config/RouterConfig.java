package io.myhealth.withings.config;

import io.myhealth.withings.service.WithingsHeartService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<ServerResponse> routes(WithingsHeartService heartHandler) {
        return route()
                .GET("/heart", heartHandler::getHeartMeasurements)
                .GET("/ecg", heartHandler::getEcgSignal)
                .build();
    }
}