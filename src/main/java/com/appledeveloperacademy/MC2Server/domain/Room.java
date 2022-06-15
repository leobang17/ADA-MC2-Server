package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.log.Log;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<MemberRoom> memberRooms = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<HealthTagActivated> activatedTags = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Log> logs = new ArrayList<>();


    public void addLog(Log log) {
        logs.add(log);
        log.setRoom(this);
    }

    public void addMemberRoom(MemberRoom memberRoom) {
        memberRooms.add(memberRoom);
        memberRoom.setRoom(this);
    }

    public void addInvitation(Invitation invitation) {
        invitation.setRoom(this);
        this.setInvitation(invitation);
    }

    public void removeActivatedTag(HealthTagActivated healthTagActivated) {
        activatedTags.remove(healthTagActivated);
        healthTagActivated.setRoom(null);
    }

    public void removeInvitation(Invitation invitation) {
        setInvitation(null);
        invitation.setRoom(null);
    }

    public static Room createRoom(MemberRoom memberRoom, Cat cat) {
        memberRoom.addCat(cat);
        Room room = new Room();
        room.addMemberRoom(memberRoom);

        return room;
    }
}
