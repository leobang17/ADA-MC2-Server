package com.appledeveloperacademy.MC2Server.dto.request;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CreateHealthTagReq {
    private String tag;

    public HealthTag build() {
        HealthTag healthTag = new HealthTag();
        healthTag.setContent(tag);
        return healthTag;
    }
}
