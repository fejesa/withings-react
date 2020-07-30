package io.myhealth.withings.config;

import io.myhealth.withings.WithingsHeartHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class WithingsRouterConfig {

    @Bean
    RouterFunction<ServerResponse> routes(WithingsHeartHandler heartHandler) {
        return route()
                .POST("/heart", accept(APPLICATION_JSON), heartHandler::getHeartMeasurement)
                .GET("/ecg", accept(APPLICATION_JSON), heartHandler::getEcgSignal)
                .GET("/heart-stream", heartHandler::getHeartMeasurementStream)
                .build();
    }
}