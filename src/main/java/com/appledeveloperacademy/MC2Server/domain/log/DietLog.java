package com.appledeveloperacademy.MC2Server.domain.log;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "D")
@Getter @Setter
public class DietLog extends Log {
    private String name;
    private int amount;
}
