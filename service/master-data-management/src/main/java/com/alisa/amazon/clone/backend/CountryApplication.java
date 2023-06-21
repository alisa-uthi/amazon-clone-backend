package com.alisa.amazon.clone.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.alisa.amazon.clone.backend.*"})
public class CountryApplication {
    public static void main(String[] args) {
        SpringApplication.run(CountryApplication.class, args);
    }
}
