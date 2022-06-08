package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.enums.Role;
import com.appledeveloperacademy.MC2Server.domain.superclass.MemberRoomIntermediate;

import javax.persistence.*;

@Entity
public class MemberRoom extends MemberRoomIntermediate {
    @Id @GeneratedValue
    @Column(name = "member_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room")
    private Room room;

    @OneToOne
    @JoinColumn(name = "cat")
    private Cat cat;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
