package com.appledeveloperacademy.MC2Server.config;

import com.appledeveloperacademy.MC2Server.service.UserService;
import com.appledeveloperacademy.MC2Server.service.impl.UserServiceMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserConfig {
    @Bean
    public UserService userService() {
        return new UserServiceMock();
    }
}
