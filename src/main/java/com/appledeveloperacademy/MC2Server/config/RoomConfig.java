package com.appledeveloperacademy.MC2Server.config;

import com.appledeveloperacademy.MC2Server.service.RoomService;
import com.appledeveloperacademy.MC2Server.service.impl.RoomServiceMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoomConfig {

    @Bean
    public RoomService roomService() {
        return new RoomServiceMock();
    }
}
