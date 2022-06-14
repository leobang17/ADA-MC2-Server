package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.log.*;
import com.appledeveloperacademy.MC2Server.dto.log.SummerizedLogDto;
import com.appledeveloperacademy.MC2Server.repository.LogRepository;
import com.appledeveloperacademy.MC2Server.repository.LogType;
import com.appledeveloperacademy.MC2Server.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Qualifier(value = "logServiceV1")
@RequiredArgsConstructor
public class LogServiceV1 implements LogService{
    private final LogRepository logRepository;

    @Override
    public List<DietLog> getDietLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset) {
        List<Log> dietLogs = logRepository.getLogsByRoomId(roomId, LogType.DIET, isPrivate, offset, limit);
        return dietLogs.stream().map(log -> (DietLog) log).collect(Collectors.toList());
    }

    @Override
    public List<WaterLog> getWaterLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset) {
        List<Log> waterLogs = logRepository.getLogsByRoomId(roomId, LogType.WATER, isPrivate, offset, limit);
        return waterLogs.stream().map(log -> (WaterLog) log).collect(Collectors.toList());
    }

    @Override
    public List<HealthLog> getHealthLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset) {
        List<Log> healthLogs = logRepository.getLogsByRoomId(roomId, LogType.HEALTH, isPrivate, offset, limit);
        return healthLogs.stream().map(log -> (HealthLog) log).collect(Collectors.toList());
    }

    @Override
    public List<MemoLog> getMemoLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset) {
        List<Log> memoLogs = logRepository.getLogsByRoomId(roomId, LogType.MEMO, isPrivate, offset, limit);
        return memoLogs.stream().map(log -> (MemoLog) log).collect(Collectors.toList());
    }

    @Override
    public SummerizedLogDto getSummerizedLogs(Long userId, Long roomId, boolean isPrivate) {
        return null;
    }
}
