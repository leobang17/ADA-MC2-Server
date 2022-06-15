package com.appledeveloperacademy.MC2Server.dto.request;

import com.appledeveloperacademy.MC2Server.domain.Coordinate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class CreateCatReq {
    private Long id;
    private int age;
    private String name;
    private String gender;
    private boolean neutralized;
    private String type;
    private String profileImgUrl;
    private Coordinate coordinate;

}
