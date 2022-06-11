package com.appledeveloperacademy.MC2Server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
@AllArgsConstructor
public class ParticipatingRoomDto {
    private Long roomId;
    private Long memberRoomId;
    private CatInfoDto cat;
}
