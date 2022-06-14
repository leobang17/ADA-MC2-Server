package com.appledeveloperacademy.MC2Server.service;

import com.appledeveloperacademy.MC2Server.domain.log.*;
import com.appledeveloperacademy.MC2Server.dto.log.HealthLogDto;
import com.appledeveloperacademy.MC2Server.dto.log.SummerizedLogDto;
import com.appledeveloperacademy.MC2Server.dto.request.CreateDietReq;
import com.appledeveloperacademy.MC2Server.dto.request.CreateMemoReq;
import com.appledeveloperacademy.MC2Server.dto.request.CreateWaterReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogService {
    List<DietLog> getDietLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset);

    List<WaterLog> getWaterLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset);

    List<HealthLog> getHealthLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset);

    List<MemoLog> getMemoLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset);

    SummerizedLogDto getSummerizedLogs(Long userId, Long roomId, boolean isPrivate);

    Long createDietLog(Long userId, Long roomId, CreateDietReq createDietReq);

    Long createWaterLog(Long userId, Long roomId, CreateWaterReq createWaterReq);

    Long createMemoLog(Long userId, Long roomId, CreateMemoReq createMemoReq);

}
