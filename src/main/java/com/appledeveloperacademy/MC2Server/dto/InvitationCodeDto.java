package com.appledeveloperacademy.MC2Server.dto;

import com.appledeveloperacademy.MC2Server.domain.Invitation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
@AllArgsConstructor
public class InvitationCodeDto {
    private String code;

    public static InvitationCodeDto build(Invitation invitation) {
        return new InvitationCodeDto(invitation.getCode());
    }
}
