package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.superclass.CreationModificationLog;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Room extends CreationModificationLog {

    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<MemberRoom> memberRooms = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<HealthTagActivated> activatedTags = new ArrayList<>();
}
