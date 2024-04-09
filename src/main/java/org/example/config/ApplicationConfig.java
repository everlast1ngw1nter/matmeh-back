package org.example.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig (
        @NonNull
        Scheduler scheduler,
        String urfuBaseUrl
) {
    public record Scheduler(boolean enable, @NonNull Duration interval) {
    }
}
