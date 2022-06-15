package com.appledeveloperacademy.MC2Server.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class AuthReq {
    private String usercode;
}
