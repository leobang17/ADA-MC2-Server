package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.superclass.CreationLog;
import com.appledeveloperacademy.MC2Server.domain.superclass.CreationModificationLog;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Invitation extends CreationLog {
    @Id @GeneratedValue
    @Column(name = "invitation_id")
    private Long id;

    @OneToOne(mappedBy = "invitation")
    private Room room;

    @Column(unique = true)
    private String code;

    private boolean fulfilled;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

}
