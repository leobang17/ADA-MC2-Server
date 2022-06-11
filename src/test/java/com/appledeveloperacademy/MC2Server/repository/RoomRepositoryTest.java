package com.appledeveloperacademy.MC2Server.repository;

import com.appledeveloperacademy.MC2Server.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class RoomRepositoryTest {

    @Autowired EntityManager em;
    @Autowired RoomRepository roomRepository;

    @Test
    void getMemberRooms() {
        Member leo = createMember("leo");
        MemberRoom memberRoom = MemberRoom.createMemberRoom(leo);
        Room room1 = Room.createRoom(memberRoom, new Cat());

        em.persist(leo);
        em.persist(room1);

    }

    @Test
    void getInvitationByCode() {
        // given
        Invitation invitation = new Invitation();
        invitation.setCode("123456");
        em.persist(invitation);

        // when
        Invitation invitationByCode = roomRepository.getInvitationByCode("123456");

        // then
        assertEquals(invitation, invitationByCode);

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