package com.appledeveloperacademy.MC2Server.dto.log;

import com.appledeveloperacademy.MC2Server.domain.log.DietLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class DietLogDto extends LogAbstract {
    private String name;
    private int amount;

    public DietLogDto(DietLog dietLog) {
        this.logId = dietLog.getId();
        this.isPublic = dietLog.isPublic();
        this.createdAt = dietLog.getCreatedAt();
        this.author = LogAuthor.build(dietLog.getMember());
        this.name = dietLog.getType();
        this.amount = dietLog.getAmount();
    }
}
