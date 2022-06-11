package com.appledeveloperacademy.MC2Server.domain.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "S")
@Getter @Setter
@AllArgsConstructor
public class SnackLog extends Log {
    private int count;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
