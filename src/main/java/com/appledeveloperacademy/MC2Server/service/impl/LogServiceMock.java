package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.domain.enums.HealthLogAction;
import com.appledeveloperacademy.MC2Server.domain.log.*;
import com.appledeveloperacademy.MC2Server.dto.log.SummerizedLogDto;
import com.appledeveloperacademy.MC2Server.dto.request.CreateDietReq;
import com.appledeveloperacademy.MC2Server.dto.request.CreateMemoReq;
import com.appledeveloperacademy.MC2Server.dto.request.CreateWaterReq;
import com.appledeveloperacademy.MC2Server.service.LogService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("logServiceMock")
public class LogServiceMock implements LogService {

    @Override
    public List<DietLog> getDietLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset) {
        DietLog log1 = dietLogBuilder(1L, "개밥", 10, true, 0L, "Leo");
        DietLog log2 = dietLogBuilder(2L, "고양이밥", 50, true, 1L, "Bang");
        DietLog log3 = dietLogBuilder(3L, "런천미트", 100, false, 1L, "Bang");
        List<DietLog> logs = new ArrayList<>();
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);
        return logs;
    }

    @Override
    public List<WaterLog> getWaterLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset) {
        WaterLog log1 = waterLogBuilder(1L, 10, true, 0L, "Leo");
        WaterLog log2 = waterLogBuilder(2L, 50, true, 1L, "Bang");
        WaterLog log3 = waterLogBuilder(3L, 100, false, 1L, "Bang");
        List<WaterLog> logs = new ArrayList<>();
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);
        return logs;
    }

    @Override
    public List<HealthLog> getHealthLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset) {
        List<HealthLog> logs = new ArrayList<>();
        HealthLog log1 = healthLogBuilder(0L, "몸이 아파요", HealthLogAction.ACTIVATE, true, 0L, "leo");
        HealthLog log2 = healthLogBuilder(1L, "눈이 빨개요.", HealthLogAction.ACTIVATE, true, 0L, "leo");
        HealthLog log3 = healthLogBuilder(2L, "다리를 절어요.", HealthLogAction.ACTIVATE, true, 1L, "bang");
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);
        return logs;
    }

    @Override
    public List<MemoLog> getMemoLogs(Long userId, Long roomId, boolean isPrivate, int limit, int offset) {
        List<MemoLog> logs = new ArrayList<>();
        MemoLog log1 = memoLogBuilder(0L, "어제 밥을 줬다", true, 0L, "leo");
        MemoLog log2 = memoLogBuilder(1L, "고롱고롱송을 부른다", true, 1L, "bang");
        MemoLog log3 = memoLogBuilder(2L, "같이 밥주는 애가 맘에 안든다.", false, 0L, "leo");
        logs.add(log1);
        logs.add(log2);
        logs.add(log3);
        return logs;
    }

    @Override
    public SummerizedLogDto getSummerizedLogs(Long userId, Long roomId, boolean isPrivate) {
        SummerizedLogDto.DietDto dietDto = new SummerizedLogDto.DietDto(dietLogBuilder(0L, "개쩌는 사료", 10, true, 0L, "Leo"));
        SummerizedLogDto.WaterDto waterDto = new SummerizedLogDto.WaterDto(waterLogBuilder(0L, 30, false, 0L, "Leo"));
        SummerizedLogDto.SnackDto snackDto = new SummerizedLogDto.SnackDto(new SnackLog(2, LocalDateTime.now()));
        SummerizedLogDto.MemoDto memoDto = new SummerizedLogDto.MemoDto(memoLogBuilder(0L, "나는 낭만고양이", true, 0L, "Leo"));
        SummerizedLogDto.HealthDto healthDto = new SummerizedLogDto.HealthDto(healthLogBuilder(0L, "배가 불러요", HealthLogAction.ACTIVATE, true, 0L, "Leo"));
        List<SummerizedLogDto.MemoDto> memoDtos = new ArrayList<>();
        List<SummerizedLogDto.HealthDto> healthDtos = new ArrayList<>();
        memoDtos.add(memoDto);
        healthDtos.add(healthDto);

        return new SummerizedLogDto(dietDto, waterDto, snackDto, healthDtos, memoDtos);
    }

    @Override
    public Long createDietLog(Long userId, Long roomId, CreateDietReq createDietReq) {
        return null;
    }

    @Override
    public Long createWaterLog(Long userId, Long roomId, CreateWaterReq createWaterReq) {
        return null;
    }

    @Override
    public Long createMemoLog(Long userId, Long roomId, CreateMemoReq createMemoReq) {
        return null;
    }

    private DietLog dietLogBuilder(Long logId, String dietName, int dietAmount, boolean isPublic, Long memberId, String username) {
        DietLog dietLog = new DietLog(dietName, dietAmount, LocalDateTime.now());
        dietLog.setId(logId);
        dietLog.setPublic(isPublic);
        dietLog.setCreatedAt(LocalDateTime.now());
        Member member = new Member();
        member.setId(memberId);
        member.setUsername(username);
        dietLog.setMember(member);
        return dietLog;
    }

    private WaterLog waterLogBuilder(Long logId, int waterAmount, boolean isPublic, Long memberId, String username) {
        WaterLog waterLog = new WaterLog(waterAmount, LocalDateTime.now());
        waterLog.setId(logId);
        waterLog.setPublic(isPublic);
        waterLog.setCreatedAt(LocalDateTime.now());
        Member member = new Member();
        member.setId(memberId);
        member.setUsername(username);
        waterLog.setMember(member);
        return waterLog;
    }

    private HealthLog healthLogBuilder(Long logId, String tag, HealthLogAction action, boolean isPublic, Long memberId, String username) {
        HealthTag healthTag = new HealthTag();
        healthTag.setContent(tag);
        HealthLog healthLog = new HealthLog(healthTag, action);
        healthLog.setId(logId);
        healthLog.setPublic(isPublic);
        healthLog.setCreatedAt(LocalDateTime.now());
        Member member = new Member();
        member.setId(memberId);
        member.setUsername(username);
        healthLog.setMember(member);
        return healthLog;
    }

    private MemoLog memoLogBuilder(Long logId, String content, boolean isPublic, Long memberId, String username) {
        MemoLog memoLog = new MemoLog(content);
        memoLog.setId(logId);
        memoLog.setPublic(isPublic);
        memoLog.setCreatedAt(LocalDateTime.now());
        Member member = new Member();
        member.setId(memberId);
        member.setUsername(username);
        memoLog.setMember(member);
        return memoLog;
    }
}
