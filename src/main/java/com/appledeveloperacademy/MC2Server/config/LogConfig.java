package com.appledeveloperacademy.MC2Server.config;

import com.appledeveloperacademy.MC2Server.service.LogService;
import com.appledeveloperacademy.MC2Server.service.impl.LogServiceMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {
    @Bean
    public LogService logService() {
        return new LogServiceMock();
    }
}
