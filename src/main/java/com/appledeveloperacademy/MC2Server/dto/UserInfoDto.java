package com.appledeveloperacademy.MC2Server.dto;

import com.appledeveloperacademy.MC2Server.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private Long userId;
    private String username;
    private String usercode;
    private LocalDateTime createdAt;

    public static UserInfoDto build(Member member) {
        return new UserInfoDto(member.getId(), member.getUsername(), member.getUsercode(), member.getCreatedAt());
    }
}
