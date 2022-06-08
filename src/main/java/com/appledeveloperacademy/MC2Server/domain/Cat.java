package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
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

    @Column(name = "img_url")
    private String imgUrl;

    private Long intimacy;

    private String type;

    @Embedded
    private Coordinate coordinate;



}
