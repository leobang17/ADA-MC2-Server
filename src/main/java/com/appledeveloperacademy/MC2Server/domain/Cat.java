package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.enums.Gender;
import com.appledeveloperacademy.MC2Server.dto.CatInfoDto;
import com.appledeveloperacademy.MC2Server.dto.request.CreateCatReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cat {
    @Id @GeneratedValue
    @Column(name = "cat_id")
    private Long id;

    @OneToOne(mappedBy = "cat")
    private MemberRoom memberRoom;

    private String name;

    private LocalDateTime birth;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private boolean neutralized;


    @Column(name = "profile_img_url")
    private String profileImgUrl;

    private Long intimacy;

    private String type;

    @Embedded
    private Coordinate coordinate;

    public Cat(CreateCatReq createCatReq) {
        this.name = createCatReq.getName();
        this.birth = ageToBirth(createCatReq.getAge());
        this.gender = Gender.parseGender(createCatReq.getGender());
        this.neutralized = createCatReq.isNeutralized();
        this.profileImgUrl = createCatReq.getProfileImgUrl();
        this.coordinate = createCatReq.getCoordinate();
        this.type = createCatReq.getType();
    }

    private LocalDateTime ageToBirth(int age) {
        LocalDateTime now = LocalDateTime.now();
        return now.minusYears(age);
    };
}
