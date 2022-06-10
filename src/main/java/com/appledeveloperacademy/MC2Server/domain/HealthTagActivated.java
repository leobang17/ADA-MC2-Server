package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.log.Log;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class HealthTagActivated {

    @Id @GeneratedValue
    @Column(name = "tag_activated_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_tag_id")
    private HealthTag healthTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    // Only if log-health's action is "ACTIVATED"
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id")
    private Log log;
}
