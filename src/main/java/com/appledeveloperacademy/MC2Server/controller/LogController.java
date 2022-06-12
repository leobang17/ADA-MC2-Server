package com.appledeveloperacademy.MC2Server.controller;

import com.appledeveloperacademy.MC2Server.domain.log.DietLog;
import com.appledeveloperacademy.MC2Server.domain.log.HealthLog;
import com.appledeveloperacademy.MC2Server.domain.log.MemoLog;
import com.appledeveloperacademy.MC2Server.domain.log.WaterLog;
import com.appledeveloperacademy.MC2Server.dto.log.*;
import com.appledeveloperacademy.MC2Server.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<DietLog> dietLogs = logService.getDietLogs(userId, roomId, isPrivate);
        List<DietLogDto> collect = dietLogs.stream().map(DietLogDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(new PagedListResult<>(collect, offset, limit));
    }

    // Create diet log for a particular room
    @PostMapping("/{roomId}/diets")
    public String createDiets(
            @PathVariable final Long roomId,
            @RequestBody final DietInputDto dietInputDto
    ) {
        return "createDiets";
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
        List<WaterLog> waterLogs = logService.getWaterLogs(userId, roomId, isPrivate);
        List<WaterLogDto> collect = waterLogs.stream().map(WaterLogDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PagedListResult<>(collect, offset, limit));
    }

    // Create watering log for a particular room
    @PostMapping("/{roomId}/waters")
    public String createWaters(
            @PathVariable final Long roomId,
            @RequestBody final WaterInputDto waterInputDto
    ) {
        return "createWaters";
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
        List<HealthLog> healthLogs = logService.getHealthLogs(userId, roomId, isPrivate);
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
        List<MemoLog> memoLogs = logService.getMemoLogs(userId, roomId, isPrivate);
        List<MemoLogDto> collect = memoLogs.stream().map(MemoLogDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(new PagedListResult<>(collect, offset, limit));
    }

    // Create memo log for a particular room
    @PostMapping("/{roomId}/memos")
    public String createMemos(
            @PathVariable final Long roomId,
            @RequestBody final MemoInputDto memoInputDto
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

    static class DietInputDto {
        private LocalDateTime time;
        private String type;
        private int amount;
    }

    static class WaterInputDto {
        private LocalDateTime time;
        private int amount;
    }

    static class HealthInputDto {
        private List<String> health;
    }

    static class MemoInputDto {
        private String content;
    }

}

