package com.appledeveloperacademy.MC2Server.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@Getter @Setter
public class Coordinate {
    private Long x;
    private Long y;
}
