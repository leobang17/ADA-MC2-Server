package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.superclass.CreationLog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class HealthTag extends CreationLog {
    @Id @GeneratedValue
    @Column(name = "health_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    public void addHealthTag(Member member) {
        this.member = member;
        member.getHealthTags().add(this);
    }
}
