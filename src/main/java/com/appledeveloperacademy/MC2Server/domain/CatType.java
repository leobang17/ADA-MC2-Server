package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.superclass.CreationLog;

import javax.persistence.*;

@Entity
public class CatType extends CreationLog {
    @Id @GeneratedValue
    @Column(name = "cat_type_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String type;
}
