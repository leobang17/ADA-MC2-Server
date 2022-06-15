package com.appledeveloperacademy.MC2Server.controller;

import com.appledeveloperacademy.MC2Server.domain.log.DietLog;
import com.appledeveloperacademy.MC2Server.domain.log.HealthLog;
import com.appledeveloperacademy.MC2Server.domain.log.MemoLog;
import com.appledeveloperacademy.MC2Server.domain.log.WaterLog;
import com.appledeveloperacademy.MC2Server.dto.log.*;
import com.appledeveloperacademy.MC2Server.dto.request.*;
import com.appledeveloperacademy.MC2Server.service.LogService;
import com.appledeveloperacademy.MC2Server.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/rooms")
@RestController
public class LogController {
    private final LogService logService;
    private final TokenService tokenService;

    public LogController(@Qualifier(value = "logServiceV1") LogService logService, TokenService tokenService) {
        this.logService = logService;
        this.tokenService = tokenService;
    }

    // List summarized logs for a particular room
    @GetMapping("/{roomId}/logs")
    public ResponseEntity<SummerizedLogDto> getLogs(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @RequestParam(name = "private", required = false, defaultValue = "false") final boolean isPrivate
    ) {
        final Long userId = tokenService.authenticateAndAuthorize(usercode, roomId);
        SummerizedLogDto summerizedLogs = logService.getSummerizedLogs(userId, roomId, isPrivate);
        return ResponseEntity.ok(summerizedLogs);
    }

    // List diet logs for a particular room
    @GetMapping("/{roomId}/diets")
    public ResponseEntity<PagedListResult<DietLogDto>> getDiets(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @RequestParam(name = "private", required = false, defaultValue = "false") final boolean isPrivate,
            @RequestParam(name = "limit", required = false, defaultValue = "20") final int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") final int offset
    ) {
        final Long userId = tokenService.authenticateAndAuthorize(usercode, roomId);
        List<DietLog> dietLogs = logService.getDietLogs(userId, roomId, isPrivate, limit, offset);
        List<DietLogDto> collect = dietLogs.stream().map(DietLogDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(new PagedListResult<>(collect, offset, limit));
    }

    // Create diet log for a particular room
    @PostMapping("/{roomId}/diets")
    public ResponseEntity createDiets(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @RequestBody final CreateDietReq createDietReq
    ) {
        final Long userId = tokenService.authenticateAndAuthorize(usercode, roomId);
        Long dietLogId = logService.createDietLog(userId, roomId, createDietReq);
        return ResponseEntity.created(URI.create("/api/rooms/" + roomId  + "/diets/" + dietLogId)).build();
    }

    // List watering logs for a particular room
    @GetMapping("/{roomId}/waters")
    public ResponseEntity<PagedListResult<WaterLogDto>> getWaters(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @RequestParam(name = "private", required = false, defaultValue = "false") final boolean isPrivate,
            @RequestParam(name = "limit", required = false, defaultValue = "20") final int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") final int offset
    ) {
        final Long userId = tokenService.authenticateAndAuthorize(usercode, roomId);
        List<WaterLog> waterLogs = logService.getWaterLogs(userId, roomId, isPrivate, limit, offset);
        List<WaterLogDto> collect = waterLogs.stream().map(WaterLogDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PagedListResult<>(collect, offset, limit));
    }

    // Create watering log for a particular room
    @PostMapping("/{roomId}/waters")
    public ResponseEntity createWaters(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @RequestBody final CreateWaterReq createWaterReq
    ) {
        final Long userId = tokenService.authenticateAndAuthorize(usercode, roomId);
        Long waterLogId = logService.createWaterLog(userId, roomId, createWaterReq);
        return ResponseEntity.created(URI.create("/api/rooms/" + roomId + "/waters/" + waterLogId)).build();
    }

    // List health logs for a particular room
    @GetMapping("/{roomId}/health")
    public ResponseEntity<PagedListResult<HealthLogDto>> getHealth(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @RequestParam(name = "private", required = false, defaultValue = "false") final boolean isPrivate,
            @RequestParam(name = "limit", required = false, defaultValue = "20") final int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") final int offset
    ) {
        final Long userId = tokenService.authenticateAndAuthorize(usercode, roomId);
        List<HealthLog> healthLogs = logService.getHealthLogs(userId, roomId, isPrivate, limit, offset);
        List<HealthLogDto> collect = healthLogs.stream().map(HealthLogDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PagedListResult<>(collect, offset, limit));
    }

    // Create health log for a particular room
    @PostMapping("/{roomId}/healths")
    public ResponseEntity createHealth(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @RequestBody final ListedRequest<CreateHealthReq> createHealthReq
    ) {
        final Long userId = tokenService.authenticateAndAuthorize(usercode, roomId);
        Long healthLog = logService.createHealthLog(userId, roomId, createHealthReq.getData());

        return ResponseEntity.created(URI.create("/api/rooms/" + roomId + "/healths")).build();
    }

    // List memo logs for a particular room
    @GetMapping("/{roomId}/memos")
    public ResponseEntity<PagedListResult<MemoLogDto>> getMemos(
            @RequestHeader(value = "Authorization") final String usercode,
            @PathVariable final Long roomId,
            @RequestParam(name = "private", required = false, defaultValue = "false") final boolean isPrivate,
            @RequestParam(name = "limit", required = false, defaultValue = "20") final int limit,
            @RequestParam(name = "offset", required = false, defaultValue = "0") final int offset
    ) {
        final Long userId = tokenService.authenticateAndAuthorize(usercode, roomId);
        List<MemoLog> memoLogs = logService.getMemoLogs(userId, roomId, isPrivate, limit, offset);
        List<MemoLogDto> collect = memoLogs.stream().map(MemoLogDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(new PagedListResult<>(collect, offset, limit));
    }

    // Create memo log for a particular room
    @PostMapping("/{roomId}/memos")
    public ResponseEntity createMemos(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @RequestBody final CreateMemoReq createMemoReq
    ) {
        final Long userId = tokenService.authenticateAndAuthorize(usercode, roomId);
        Long memoLogId = logService.createMemoLog(userId, roomId, createMemoReq);
        return ResponseEntity.created(URI.create("/api/rooms" + roomId + "/memos/" + memoLogId)).build();
    }

    // Increase snack log for a particular room
    @PutMapping("/{roomId}/snacks/{snackId}")
    public ResponseEntity increaseSnacks(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @PathVariable final Long snackId
    ) {
        final Long userId = tokenService.authenticateAndAuthorize(usercode, roomId);
        logService.increaseSnack(roomId, snackId);
        return ResponseEntity.ok().build();
    }
}

