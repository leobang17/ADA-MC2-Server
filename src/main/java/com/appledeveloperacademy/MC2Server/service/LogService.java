package com.appledeveloperacademy.MC2Server.service;

import com.appledeveloperacademy.MC2Server.domain.log.DietLog;
import com.appledeveloperacademy.MC2Server.domain.log.HealthLog;
import com.appledeveloperacademy.MC2Server.domain.log.MemoLog;
import com.appledeveloperacademy.MC2Server.domain.log.WaterLog;
import com.appledeveloperacademy.MC2Server.dto.log.HealthLogDto;
import com.appledeveloperacademy.MC2Server.dto.log.SummerizedLogDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogService {
    List<DietLog> getDietLogs(Long userId, Long roomId, boolean isPrivate);

    List<WaterLog> getWaterLogs(Long userId, Long roomId, boolean isPrivate);

    List<HealthLog> getHealthLogs(Long userId, Long roomId, boolean isPrivate);

    List<MemoLog> getMemoLogs(Long userId, Long roomId, boolean isPrivate);

    SummerizedLogDto getSummerizedLogs(Long userId, Long roomId, boolean isPrivate);

}
