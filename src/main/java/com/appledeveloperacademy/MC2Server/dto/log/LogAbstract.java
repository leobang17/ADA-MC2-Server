package com.appledeveloperacademy.MC2Server.dto.log;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class LogAbstract {
    protected Long logId;
    protected boolean isPublic;
    protected LocalDateTime createdAt;
    protected LogAuthor author;
}
