package com.appledeveloperacademy.MC2Server.dto.request;

import com.appledeveloperacademy.MC2Server.domain.log.WaterLog;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter @Setter
public class CreateWaterReq {
    private LocalDateTime time;
    private int amount;

    public WaterLog buildLog() {
        WaterLog waterLog = new WaterLog();
        waterLog.setCreatedAt(time);
        waterLog.setAmount(amount);
        return waterLog;
    }
}
