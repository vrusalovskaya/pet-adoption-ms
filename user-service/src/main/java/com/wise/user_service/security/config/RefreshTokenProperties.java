package com.wise.user_service.security.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@Valid
@ConfigurationProperties(prefix = "security.refresh-token")
public class RefreshTokenProperties {

    @NotNull
    private Duration ttl;
}