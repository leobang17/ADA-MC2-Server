package com.appledeveloperacademy.MC2Server.domain.log;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "W")
@Getter @Setter
public class WaterLog extends Log{
    private int amount;
}
