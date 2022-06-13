package com.appledeveloperacademy.MC2Server.dto;

import com.appledeveloperacademy.MC2Server.domain.MemberRoom;
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

    public static ParticipatingRoomDto build(MemberRoom memberRoom) {
        CatInfoDto catInfoDto = new CatInfoDto(memberRoom.getCat());
        return new ParticipatingRoomDto(memberRoom.getRoom().getId(), memberRoom.getId(), catInfoDto);
    }
}
