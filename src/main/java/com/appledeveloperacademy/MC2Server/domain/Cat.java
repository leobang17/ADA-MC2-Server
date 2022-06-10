package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Cat {
    @Id @GeneratedValue
    @Column(name = "cat_id")
    private Long id;

    @OneToOne(mappedBy = "cat")
    private MemberRoom memberRoom;

    private String name;

    private int age;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private boolean neutralized;

    @Column(name = "main_img_url")
    private String mainImgUrl;

    @Column(name = "profile_img_url")
    private String profileImgUrl;

    private Long intimacy;

    private String type;

    @Embedded
    private Coordinate coordinate;



}
