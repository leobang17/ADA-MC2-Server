package com.appledeveloperacademy.MC2Server.dto.log;

import com.appledeveloperacademy.MC2Server.domain.log.MemoLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MemoLogDto extends LogAbstract {
    private String content;

    public MemoLogDto(MemoLog memoLog) {
        this.content = memoLog.getContent();
        this.logId = memoLog.getId();
        this.isPublic = memoLog.isPublic();
        this.createdAt = memoLog.getCreatedAt();
        this.author = LogAuthor.build(memoLog.getMember());
    }
}
