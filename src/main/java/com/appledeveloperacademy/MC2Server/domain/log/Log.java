package com.appledeveloperacademy.MC2Server.domain.log;

import com.appledeveloperacademy.MC2Server.domain.superclass.CreationModificationLog;
import com.appledeveloperacademy.MC2Server.domain.superclass.MemberRoomIntermediate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Table(name = "logs")
@Getter @Setter
public abstract class Log extends MemberRoomIntermediate {
    @Id @GeneratedValue
    @Column(name = "log_id")
    private Long id;

    @Column(name = "public")
    private boolean isPublic;
}
