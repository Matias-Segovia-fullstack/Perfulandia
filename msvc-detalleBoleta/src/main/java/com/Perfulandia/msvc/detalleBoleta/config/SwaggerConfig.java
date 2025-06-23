package com.Perfulandia.msvc.detalleBoleta.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API DetalleBoleta")
                        .version("1.0")
                        .description("Documentación del microservicio DetalleBoleta de Perfulandia"));
    }
}