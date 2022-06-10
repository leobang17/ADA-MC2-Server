package com.appledeveloperacademy.MC2Server.dto;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HealthTagDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    public HealthTagDto(HealthTag healthTag) {
        this.id = healthTag.getId();
        this.content = healthTag.getContent();
        this.createdAt = healthTag.getCreatedAt();
    }
}
