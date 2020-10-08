package io.myhealth.withings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration
public class WebConfig {

    @Bean
    CorsWebFilter corsFilter() {
        return new CorsWebFilter(exchange -> new CorsConfiguration().applyPermitDefaultValues());
    }
}
