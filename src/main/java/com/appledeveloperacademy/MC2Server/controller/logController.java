package com.appledeveloperacademy.MC2Server.controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/rooms")
@RestController
public class logController {

    // List summarized logs for a particular room
    @GetMapping("/{roomId}/logs")
    public String getLogs(
            @PathVariable final Long roomId,
            @RequestParam(name = "private", defaultValue = "false") final boolean isPrivate
    ) {
        return "getLogs";
    }

    // List diet logs for a particular room
    @GetMapping("/{roomId}/diets")
    public String getDiets(
            @PathVariable final Long roomId,
            @RequestParam(name = "private", defaultValue = "false") final boolean isPrivate
    ) {
        return "getDiets";
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
    public String getWaters(
            @PathVariable final Long roomId,
            @RequestParam(name = "private", defaultValue = "false") final boolean isPrivate
    ) {
        return "getWaters";
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
    public String getHealth(
            @PathVariable final Long roomId,
            @RequestParam(name = "private", defaultValue = "false") final boolean isPrivate
    ) {
        return "getHealth";
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
    public String getMemos(
            @PathVariable final Long roomId,
            @RequestParam(name = "private", defaultValue = "false") final boolean isPrivate
    ) {
        return "getMemos";
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

