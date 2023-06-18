package com.example.apibank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

// http://localhost:8080/swagger-ui/index.html#/ Acceso a la documentación de la API :)
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Bank iobuilders")
                        .description("Lo que no conviene a la colmena, no conviene a la abeja")
                        .version("1.0"));
    }
}