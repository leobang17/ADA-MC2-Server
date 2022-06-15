package com.appledeveloperacademy.MC2Server.domain;


import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {
    private Long x;
    private Long y;
}
