package org.seguridad.api_rest.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api(){
    return GroupedOpenApi.builder()
                .group("api")
                .packagesToScan("org.seguridad.api_rest.controllers")
                .build();
    }
}
