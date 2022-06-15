package com.appledeveloperacademy.MC2Server.dto.request;

import com.appledeveloperacademy.MC2Server.domain.Coordinate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class CreateCatReq {
    private String name;
    private String gender;
    private int age;
    private String type;
    private boolean neutralized;
    private String profileImgUrl;
    @JsonProperty("coordinate")
    private Coordinate coordinate;
}
