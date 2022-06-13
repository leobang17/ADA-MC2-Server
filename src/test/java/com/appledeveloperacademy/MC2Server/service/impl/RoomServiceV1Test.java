package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.*;
import com.appledeveloperacademy.MC2Server.dto.InvitationCodeDto;
import com.appledeveloperacademy.MC2Server.repository.RoomRepository;
import com.appledeveloperacademy.MC2Server.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
class RoomServiceV1Test {
    @Autowired
    EntityManager em;
    @Autowired
    RoomServiceV1 roomService;

    @Test
    void findInvitationCodeByRoomId() {
        // given
        Room room = new Room();
        Invitation invitation = new Invitation();
        invitation.setCode("123456");
        room.setInvitation(invitation);
        invitation.setRoom(room);
        Room room1 = new Room();
        em.persist(room);
        em.persist(room1);

        // when
        em.flush();
        Invitation invitationCodeByRoomId = roomService.findInvitationCodeByRoomId(room.getId());

        // then
        assertEquals(invitation, invitationCodeByRoomId);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            roomService.findInvitationCodeByRoomId(room1.getId());
        });

    }

    @Test
    void findAllParticipatingRooms() {
        // given
        Member member = new Member();
        MemberRoom memberRoom = new MemberRoom();
        MemberRoom memberRoom1 = new MemberRoom();
        MemberRoom memberRoom2 = new MemberRoom();

        member.addMemberRoom(memberRoom);
        member.addMemberRoom(memberRoom1);
        member.addMemberRoom(memberRoom2);
        em.persist(member);

        // when
        em.flush();
        List<MemberRoom> allParticipatingRooms = roomService.findAllParticipatingRooms(member.getId());

        // then
        assertEquals(3, allParticipatingRooms.size());
    }

    @Test
    void findCatByCatId() {
        // given
        Cat cat = new Cat();
        em.persist(cat);

        // when
        em.flush();
        Cat catByCatId = roomService.findCatByCatId(cat.getId());

        // then
        assertEquals(cat, catByCatId);
    }
}