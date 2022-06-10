package com.appledeveloperacademy.MC2Server.domain.log;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "D")
@Getter @Setter
public class DietLog extends Log {
    private String name;
    private int amount;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
