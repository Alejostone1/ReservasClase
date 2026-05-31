package com.juniorstone.reservation_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI/Swagger documentation.
 * Provides API documentation accessible at /swagger-ui/index.html
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates the OpenAPI configuration bean.
     *
     * @return the configured OpenAPI instance
     */
    @Bean
    public OpenAPI reservationOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ReservaT API")
                        .description("API REST para el sistema de reservas ReservaT")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("ReservaT Team")
                                .email("contact@reservat.com")));
    }
}
