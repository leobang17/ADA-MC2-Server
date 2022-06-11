package com.appledeveloperacademy.MC2Server.dto;

import com.appledeveloperacademy.MC2Server.domain.Cat;
import com.appledeveloperacademy.MC2Server.domain.Coordinate;
import com.appledeveloperacademy.MC2Server.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
@AllArgsConstructor
public class CatInfoDto {
    private Long id;
    private int age;
    private String name;
    private String gender;
    private boolean neutralized;
    private String type;
    private String mainImgUrl;
    private String profileImgUrl;
    private Coordinate coordinate;

    public CatInfoDto(Cat cat) {
        this.id = cat.getId();
        this.age = cat.getAge();
        this.name = cat.getName();
        this.neutralized = cat.isNeutralized();
        this.gender = cat.getGender().name();
        this.type = cat.getType();
        this.mainImgUrl = cat.getMainImgUrl();
        this.profileImgUrl = cat.getProfileImgUrl();
        this.coordinate = cat.getCoordinate();
    }
}
