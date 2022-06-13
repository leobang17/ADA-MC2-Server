package com.appledeveloperacademy.MC2Server.repository;

import com.appledeveloperacademy.MC2Server.domain.*;
import com.appledeveloperacademy.MC2Server.dto.request.CreateCatReq;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class RoomRepositoryTest {

    @Autowired EntityManager em;
    @Autowired RoomRepository roomRepository;
    @Autowired UserRepository userRepository;

    @Test
    void createRoomTest() {
        Member member = new Member();
        Cat cat = new Cat();
        Room room = new Room();
        MemberRoom memberRoom = MemberRoom.createMemberRoom(member, cat, room);
        room.addMemberRoom(memberRoom);

        em.flush();
        roomRepository.createRoom(room);

        MemberRoom memberRoom1 = em.find(MemberRoom.class, memberRoom.getId());
        assertEquals(member, memberRoom1.getMember());
        assertEquals(cat, memberRoom1.getCat());
        assertEquals(room, memberRoom1.getRoom());
    }

    @Test
    void createRoomTestV1() {
        // find member
        Member member = new Member();
        em.persist(member);

        Member findUser = userRepository.findById(member.getId());
        System.out.println("유저 이이디" + findUser.getId());

        // create instances
        Room room = new Room();
        Cat cat = new Cat();
        MemberRoom memberRoom = new MemberRoom();

        // connect
        memberRoom.setRoom(room);
        room.getMemberRooms().add(memberRoom);
        member.addMemberRoom(memberRoom);

        memberRoom.setCat(cat);
        cat.setMemberRoom(memberRoom);

        // persist & flush
//        em.persist(memberRoom);
//        em.persist(cat);
        em.persist(room);
        em.flush();

        MemberRoom memberRoom1 = em.find(MemberRoom.class, memberRoom.getId());
        assertEquals(memberRoom1.getRoom(), room);
        assertEquals(memberRoom1.getCat(), cat);
        assertEquals(memberRoom1.getMember(), member);
    }

    @Test
    void getMemberRooms() {
        Member leo = createMember("leo");
        MemberRoom memberRoom = MemberRoom.createMemberRoom(leo);
        Room room1 = Room.createRoom(memberRoom, new Cat());

        em.persist(leo);
        em.persist(room1);

    }

    @Test
    void invitationRemovalTest() {
        // given
        Invitation invitation = new Invitation();
        Room room = new Room();
        invitation.setRoom(room);
        room.setInvitation(invitation);

        em.persist(room);

        // when
//        em.flush();

//        roomRepository.removeInvitation(invitation);
//        em.remove(invitation);
        int id = em.createQuery("DELETE FROM Invitation i WHERE i.id = :id").setParameter("id", invitation.getId()).executeUpdate();

//        em.flush();
        Room room1 = em.find(Room.class, room.getId());
        Invitation invitation1 = em.find(Invitation.class, invitation.getId());

        // then
        assertNull(room1.getInvitation());
//        assertNull(invitation1);

    }

    @Test
    void getInvitationByCode() {
        // given
        Invitation invitation = new Invitation();
        invitation.setCode("123456");
        em.persist(invitation);

        // when
        List<Invitation> invitationByCode = roomRepository.getInvitationByCode("123456");

        // then
        assertEquals(invitation, invitationByCode.get(0));

    }

    @Test
    void getInvitationByRoomId() {
        // given
        Invitation invitation = new Invitation();
        invitation.setCode("123456");
        em.persist(invitation);

        Room room = new Room();
        room.setInvitation(invitation);
        em.persist(room);

        // when
        em.flush();
        Invitation find = roomRepository.getInvitationByRoomId(room.getId());

        // then
        assertEquals(invitation, find);
    }

    @Test
    void findCatTest() {
        // given
        Cat cat = new Cat();
        em.persist(cat);

        // when
        em.flush();
        Cat catById = roomRepository.findCatById(cat.getId());

        assertEquals(cat, catById);
    }

    private Member createMember(String username) {
        Member member = new Member();
        member.setUsername(username);
        String s = UUID.randomUUID().toString();
        member.setUsercode(s);
        return member;
    }
}