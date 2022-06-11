package com.appledeveloperacademy.MC2Server.dto.log;

import com.appledeveloperacademy.MC2Server.domain.log.WaterLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WaterLogDto extends LogAbstract{
    private int amount;

    public WaterLogDto(WaterLog waterLog) {
        this.logId = waterLog.getId();
        this.isPublic = waterLog.isPublic();
        this.createdAt = waterLog.getCreatedAt();
        this.author = LogAuthor.build(waterLog.getMember());
        this.amount = waterLog.getAmount();
    }
}
