package com.wise.user_service.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class DateTimeConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}