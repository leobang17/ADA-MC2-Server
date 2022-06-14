package com.appledeveloperacademy.MC2Server.controller;

import com.appledeveloperacademy.MC2Server.domain.log.DietLog;
import com.appledeveloperacademy.MC2Server.domain.log.HealthLog;
import com.appledeveloperacademy.MC2Server.domain.log.MemoLog;
import com.appledeveloperacademy.MC2Server.domain.log.WaterLog;
import com.appledeveloperacademy.MC2Server.dto.log.*;
import com.appledeveloperacademy.MC2Server.dto.request.CreateDietReq;
import com.appledeveloperacademy.MC2Server.dto.request.CreateMemoReq;
import com.appledeveloperacademy.MC2Server.dto.request.CreateWaterReq;
import com.appledeveloperacademy.MC2Server.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/rooms")
@RestController
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;

    // List summarized logs for a particular room
    @GetMapping("/{roomId}/logs")
    public ResponseEntity<SummerizedLogDto> getLogs(
            @PathVariable final Long roomId,
            @RequestParam(name = "private", required = false, defaultValue = "false") final boolean isPrivate
    ) {
        final Long userId = 0L;
        SummerizedLogDto summerizedLogs = logService.getSummerizedLogs(userId, roomId, isPrivate);
        return ResponseEntity.ok(summerizedLogs);
    }

    // List diet logs for a particular room
    @GetMapping("/{roomId}/diets")
    public ResponseEntity<PagedListResult<DietLogDto>> getDiets(
            @PathVariable final Long roomId,
            @RequestParam(name = "private", required = false, defaultValue = "false") final boolean isPrivate,
            @RequestParam(name = "limit", required = false, defaultValue = "20") final int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") final int offset
    ) {
        final Long userId = 0L;
        List<DietLog> dietLogs = logService.getDietLogs(userId, roomId, isPrivate, limit, offset);
        List<DietLogDto> collect = dietLogs.stream().map(DietLogDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(new PagedListResult<>(collect, offset, limit));
    }

    // Create diet log for a particular room
    @PostMapping("/{roomId}/diets")
    public ResponseEntity createDiets(
            @PathVariable final Long roomId,
            @RequestBody final CreateDietReq createDietReq
    ) {
        final Long userId = 0L;
        Long dietLogId = logService.createDietLog(userId, roomId, createDietReq);
        return ResponseEntity.created(URI.create("/api/rooms/" + roomId  + "/diets/" + dietLogId)).build();
    }

    // List watering logs for a particular room
    @GetMapping("/{roomId}/waters")
    public ResponseEntity<PagedListResult<WaterLogDto>> getWaters(
            @PathVariable final Long roomId,
            @RequestParam(name = "private", required = false, defaultValue = "false") final boolean isPrivate,
            @RequestParam(name = "limit", required = false, defaultValue = "20") final int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") final int offset
    ) {
        final Long userId = 0L;
        List<WaterLog> waterLogs = logService.getWaterLogs(userId, roomId, isPrivate, limit, offset);
        List<WaterLogDto> collect = waterLogs.stream().map(WaterLogDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PagedListResult<>(collect, offset, limit));
    }

    // Create watering log for a particular room
    @PostMapping("/{roomId}/waters")
    public ResponseEntity createWaters(
            @PathVariable final Long roomId,
            @RequestBody final CreateWaterReq createWaterReq
    ) {
        final Long userId = 0L;
        Long waterLogId = logService.createWaterLog(userId, roomId, createWaterReq);
        return ResponseEntity.created(URI.create("/api/rooms/" + roomId + "/waters/" + waterLogId)).build();
    }

    // List health logs for a particular room
    @GetMapping("/{roomId}/health")
    public ResponseEntity<PagedListResult<HealthLogDto>> getHealth(
            @PathVariable final Long roomId,
            @RequestParam(name = "private", required = false, defaultValue = "false") final boolean isPrivate,
            @RequestParam(name = "limit", required = false, defaultValue = "20") final int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") final int offset
    ) {
        final Long userId = 0L;
        List<HealthLog> healthLogs = logService.getHealthLogs(userId, roomId, isPrivate, limit, offset);
        List<HealthLogDto> collect = healthLogs.stream().map(HealthLogDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PagedListResult<>(collect, offset, limit));
    }

    // Create health log for a particular room
    @PostMapping("/{roomId}/healths")
    public String createHealth(
            @PathVariable final Long roomId,
            @RequestBody final HealthInputDto healthInputDto
    ) {
        return "createHealth";
    }

    // List memo logs for a particular room
    @GetMapping("/{roomId}/memos")
    public ResponseEntity<PagedListResult<MemoLogDto>> getMemos(
            @PathVariable final Long roomId,
            @RequestParam(name = "private", required = false, defaultValue = "false") final boolean isPrivate,
            @RequestParam(name = "limit", required = false, defaultValue = "20") final int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") final int offset
    ) {
        final Long userId = 0L;
        List<MemoLog> memoLogs = logService.getMemoLogs(userId, roomId, isPrivate, limit, offset);
        List<MemoLogDto> collect = memoLogs.stream().map(MemoLogDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(new PagedListResult<>(collect, offset, limit));
    }

    // Create memo log for a particular room
    @PostMapping("/{roomId}/memos")
    public String createMemos(
            @PathVariable final Long roomId,
            @RequestBody final CreateMemoReq createMemoReq
    ) {

        return "createMemos";
    }

    // Increase snack log for a particular room
    @PutMapping("/{roomId}/snacks")
    public String increaseSnacks(
            @PathVariable final Long roomId
    ) {
        return "increaseSnacks";
    }


    static class HealthInputDto {
        private List<String> health;
    }
}

