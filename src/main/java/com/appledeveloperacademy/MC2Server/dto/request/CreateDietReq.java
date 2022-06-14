package com.appledeveloperacademy.MC2Server.dto.request;

import com.appledeveloperacademy.MC2Server.domain.log.DietLog;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter @Setter
public class CreateDietReq {
    private LocalDateTime time;
    private String type;
    private int amount;

    public DietLog buildLog() {
        DietLog dietLog = new DietLog();
        dietLog.setCreatedAt(time);
        dietLog.setType(type);
        dietLog.setAmount(amount);
        return dietLog;
    }
}
