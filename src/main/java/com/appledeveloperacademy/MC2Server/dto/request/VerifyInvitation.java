package com.appledeveloperacademy.MC2Server.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class VerifyInvitation {
    private String code;
}
