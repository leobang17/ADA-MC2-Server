package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.HealthTagActivated;
import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.domain.Room;
import com.appledeveloperacademy.MC2Server.domain.enums.HealthLogAction;
import com.appledeveloperacademy.MC2Server.domain.log.*;
import com.appledeveloperacademy.MC2Server.dto.log.SummerizedLogDto;
import com.appledeveloperacademy.MC2Server.dto.request.*;
import com.appledeveloperacademy.MC2Server.repository.LogRepository;
import com.appledeveloperacademy.MC2Server.repository.LogType;
import com.appledeveloperacademy.MC2Server.repository.RoomRepository;
import com.appledeveloperacademy.MC2Server.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

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

    @Override
    @Transactional
    public Long createDietLog(Long userId, Long roomId, CreateDietReq createDietReq) {
        Member member = userRepository.findById(userId);
        Room room = roomRepository.findRoomByRoomId(roomId);

        DietLog dietLog = createDietReq.buildLog();
        dietLog.writeLog(member, room);

        logRepository.flush();
        return dietLog.getId();
    }

    @Override
    @Transactional
    public Long createWaterLog(Long userId, Long roomId, CreateWaterReq createWaterReq) {
        Member member = userRepository.findById(userId);
        Room room = roomRepository.findRoomByRoomId(roomId);

        WaterLog waterLog = createWaterReq.buildLog();
        waterLog.writeLog(member, room);

        logRepository.flush();
        return waterLog.getId();
    }

    @Override
    @Transactional
    public Long createMemoLog(Long userId, Long roomId, CreateMemoReq createMemoReq) {

        Member member = userRepository.findById(userId);
        Room room = roomRepository.findRoomByRoomId(roomId);

        MemoLog memoLog = createMemoReq.buildLog();
        memoLog.writeLog(member, room);

        logRepository.flush();
        return memoLog.getId();
    }

    @Override
    @Transactional
    public Long createHealthLog(Long userId, Long roomId, List<CreateHealthReq> createHealthReq) {
        List<HealthTag> tags = logRepository.listHealthTagsActivated(roomId);
        Member member = userRepository.findById(userId);
        Room room = roomRepository.findRoomByRoomId(roomId);

        // get tags with the id;
        for (CreateHealthReq req : createHealthReq) {
            Long tagId = req.getTagId();
            HealthTag healthTag = logRepository.findHealthTagById(tagId);

            // find tag if it's activated
            final boolean isActivated = tags.stream().anyMatch(t -> t.getId() == tagId);
            HealthLogAction action;


            // if activated
            if (isActivated) {
                action = HealthLogAction.DEACTIVATE;
                // Do: delete from activated and create log
                HealthTagActivated healthTagActivated = logRepository.findHealthTagActivate(roomId, tagId);
                logRepository.deactivateHealthTag(healthTagActivated, room);

                // else (deactivated)
            } else {
                // Do: append to activated list and create log
                action = HealthLogAction.ACTIVATE;
                HealthTagActivated healthTagActivated = new HealthTagActivated();
                healthTagActivated.activate(healthTag, room);
            }

            HealthLog healthLog = new HealthLog();
            healthLog.setHealthTag(healthTag);
            healthLog.setAction(action);
            healthLog.writeLog(member, room);

            logRepository.flush();
        }

        return null;
    }


}
