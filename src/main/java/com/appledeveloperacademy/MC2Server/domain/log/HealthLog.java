package com.appledeveloperacademy.MC2Server.domain.log;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.enums.HealthLogAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "H")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthLog extends Log {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_tag_id")
    private HealthTag healthTag;

    private HealthLogAction action;
}
