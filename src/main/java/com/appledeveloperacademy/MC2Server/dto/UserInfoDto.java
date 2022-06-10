package com.appledeveloperacademy.MC2Server.dto;

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
}
