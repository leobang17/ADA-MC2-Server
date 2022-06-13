package com.appledeveloperacademy.MC2Server.domain;

import com.appledeveloperacademy.MC2Server.domain.enums.Role;
import com.appledeveloperacademy.MC2Server.domain.superclass.MemberRoomIntermediate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class MemberRoom extends MemberRoomIntermediate {
    @Id @GeneratedValue
    @Column(name = "member_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cat_id")
    private Cat cat;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public static MemberRoom createMemberRoom(Member member) {
        MemberRoom memberRoom = new MemberRoom();
        memberRoom.setMember(member);
        List<MemberRoom> memberRooms = member.getMemberRooms();
        memberRooms.add(memberRoom);
        return memberRoom;
    }

    public static MemberRoom createMemberRoom(Member member, Cat cat, Room room) {
        MemberRoom memberRoom = new MemberRoom();
        // connect with cat
        memberRoom.addCat(cat);
        // connect with member
        member.addMemberRoom(memberRoom);
        // connect with room
        room.addMemberRoom(memberRoom);
        return memberRoom;
    }

    public void addCat(Cat cat) {
        cat.setMemberRoom(this);
        this.setCat(cat);
    }

    private void addMember(Member member) {
        this.setMember(member);
        member.getMemberRooms().add(this);
    }
}
