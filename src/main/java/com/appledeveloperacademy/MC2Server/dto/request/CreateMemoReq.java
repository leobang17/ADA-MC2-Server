package com.appledeveloperacademy.MC2Server.dto.request;

import com.appledeveloperacademy.MC2Server.domain.log.MemoLog;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter @Getter
public class CreateMemoReq {
    private String content;

    public MemoLog buildLog() {
        MemoLog memoLog = new MemoLog();
        memoLog.setContent(content);
        return memoLog;
    }
}
