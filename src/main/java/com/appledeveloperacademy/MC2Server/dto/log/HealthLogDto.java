package com.appledeveloperacademy.MC2Server.dto.log;

import com.appledeveloperacademy.MC2Server.domain.enums.HealthLogAction;
import com.appledeveloperacademy.MC2Server.domain.log.HealthLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HealthLogDto extends LogAbstract {
    private String tag;
    private String action;

    public HealthLogDto(HealthLog healthLog) {
        this.tag = healthLog.getHealthTag().getContent();
        this.action = healthLog.getAction().name();
        this.logId = healthLog.getId();
        this.isPublic = healthLog.isPublic();
        this.createdAt = healthLog.getCreatedAt();
        this.author = LogAuthor.build(healthLog.getMember());
    }
}
