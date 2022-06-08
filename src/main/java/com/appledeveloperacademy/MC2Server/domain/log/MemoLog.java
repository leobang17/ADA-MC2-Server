package com.appledeveloperacademy.MC2Server.domain.log;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "M")
@Getter @Setter
public class MemoLog extends Log {
    private String content;
}
