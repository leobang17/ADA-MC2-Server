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

import java.util.ArrayList;
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
    @Transactional
    public SummerizedLogDto getSummerizedLogs(Long userId, Long roomId, boolean isPrivate) {
        SummerizedLogDto.DietDto dietDto = null;
        SummerizedLogDto.WaterDto waterDto = null;
        SummerizedLogDto.SnackDto snackDto = null;
        List<SummerizedLogDto.HealthDto> healthDtos = null;
        List<SummerizedLogDto.MemoDto> memoDtos = null;

        List<Log> dietLog = logRepository.getTodayLogsByRoomId(roomId, LogType.DIET, isPrivate, 0, 1);
        if (dietLog.size() > 0) {
            List<SummerizedLogDto.DietDto> dietDtoList = dietLog.stream().map(o -> new SummerizedLogDto.DietDto((DietLog) o)).collect(Collectors.toList());
            dietDto = dietDtoList.get(0);
        }

        List<Log> waterLog = logRepository.getTodayLogsByRoomId(roomId, LogType.WATER, isPrivate, 0, 1);
        if (waterLog.size() > 0) {
            List<SummerizedLogDto.WaterDto> waterDtoList = waterLog.stream().map(o -> new SummerizedLogDto.WaterDto((WaterLog) o)).collect(Collectors.toList());
            waterDto = waterDtoList.get(0);
        }

        List<Log> snackLog = logRepository.getTodayLogsByRoomId(roomId, LogType.SNACK, isPrivate, 0, 1);
        if (snackLog.size() == 0) {
            // today's snack log is not created
            // Do: create snack log for today
            createSnackLog(userId, roomId);
            snackLog = logRepository.getTodayLogsByRoomId(roomId, LogType.SNACK, isPrivate, 0, 1);
        }
        snackDto = new SummerizedLogDto.SnackDto((SnackLog) snackLog.get(0));


        List<HealthTag> tags = logRepository.listHealthTagsActivated(roomId);
        healthDtos = tags.stream().map(SummerizedLogDto.HealthDto::new).collect(Collectors.toList());

        List<Log> memoLogs = logRepository.getLogsByRoomId(roomId, LogType.MEMO, isPrivate, 0, 10);
        memoDtos = memoLogs.stream().map(o -> new SummerizedLogDto.MemoDto((MemoLog) o)).collect(Collectors.toList());

        return new SummerizedLogDto(dietDto, waterDto, snackDto, healthDtos, memoDtos);
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

    private Long createSnackLog(Long userId, Long roomId) {
        Member member = userRepository.findById(userId);
        Room room = roomRepository.findRoomByRoomId(roomId);

        SnackLog snackLog = new SnackLog();
        snackLog.writeLog(member, room);

        logRepository.flush();
        return snackLog.getId();
    }


}
