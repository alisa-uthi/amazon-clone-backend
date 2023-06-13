package com.alisa.amazon.clone.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${spring.application.name:Default}")
    String applicationName;

    @Value("${spring.application.version:0.0.1}")
    String applicationVersion;

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("My Backend for Frontend APIs.")
                .description("Microservice: ".concat(applicationName))
                .contact(new Contact()
                        .name("Alisa Uthikamporn")
                        .email("alisauthi@gmail.com"))
                .version(applicationVersion);
    }
}
