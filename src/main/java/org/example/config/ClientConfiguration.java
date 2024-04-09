package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    public ClientConfiguration(@Autowired ApplicationConfig applicationConfig){
        this.applicationConfig = applicationConfig;
    }

    private final ApplicationConfig applicationConfig;

    @Bean
    public WebClient urfuClient() {
        return WebClient
                .builder()
                .baseUrl(applicationConfig.urfuBaseUrl())
                .build();
    }
}

