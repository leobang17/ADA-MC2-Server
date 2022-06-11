package com.appledeveloperacademy.MC2Server.dto.log;

import com.appledeveloperacademy.MC2Server.domain.log.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SummerizedLogDto {
    private DietDto diet;
    private WaterDto water;
    private SnackDto snack;
    private List<HealthDto> health = new ArrayList<>();
    private List<MemoDto> memo = new ArrayList<>();

    @Data
    public static class DietDto {
        private Long id;
        private String type;
        private int amount;
        private LocalDateTime timestamp;
        private LogAuthor author;

        public DietDto(DietLog dietLog) {
            this.id = dietLog.getId();
            this.type = dietLog.getType();
            this.amount = dietLog.getAmount();
            this.timestamp = dietLog.getCreatedAt();
            this.author = LogAuthor.build(dietLog.getMember());
        }
    }

    @Data
    public static class WaterDto {
        private Long id;
        private int amount;
        private LocalDateTime timestamp;
        private LogAuthor author;

        public WaterDto(WaterLog waterLog) {
            this.id = waterLog.getId();
            this.amount = waterLog.getAmount();
            this.timestamp = waterLog.getCreatedAt();
            this.author = LogAuthor.build(waterLog.getMember());
        }
    }

    @Data
    public static class SnackDto {
        private int count;

        public SnackDto(SnackLog snackLog) {
            this.count = snackLog.getCount();
        }
    }

    @Data
    public static class HealthDto {
        private Long id;
        private String content;
        private LogAuthor author;
        private LocalDateTime timestamp;

        public HealthDto(HealthLog healthLog) {
            this.id = healthLog.getId();
            this.content = healthLog.getHealthTag().getContent();
            this.timestamp = healthLog.getCreatedAt();
            this.author = LogAuthor.build(healthLog.getMember());
        }
    }

    @Data
    public static class MemoDto {
        private Long id;
        private String content;
        private LogAuthor author;
        private LocalDateTime timestamp;

        public MemoDto(MemoLog memoLog) {
            this.id = memoLog.getId();
            this.content = memoLog.getContent();
            this.author = LogAuthor.build(memoLog.getMember());
            this.timestamp = memoLog.getCreatedAt();
        }
    }
}
