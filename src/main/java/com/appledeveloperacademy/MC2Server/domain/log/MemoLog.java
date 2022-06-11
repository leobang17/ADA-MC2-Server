package com.appledeveloperacademy.MC2Server.domain.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "M")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemoLog extends Log {
    private String content;
}
