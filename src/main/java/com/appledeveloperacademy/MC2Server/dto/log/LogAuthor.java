package com.appledeveloperacademy.MC2Server.dto.log;

import com.appledeveloperacademy.MC2Server.domain.Member;
import lombok.Data;

@Data
public class LogAuthor {
    private Long userId;
    private String username;

    private LogAuthor(Member member) {
        this.userId = member.getId();
        this.username = member.getUsername();
    }
    public static LogAuthor build(Member member) {
        return new LogAuthor(member);
    }
}
