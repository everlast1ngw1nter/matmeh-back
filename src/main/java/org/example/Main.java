package org.example;

import org.example.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}