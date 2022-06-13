package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.*;
import com.appledeveloperacademy.MC2Server.domain.enums.Gender;
import com.appledeveloperacademy.MC2Server.dto.InvitationCodeDto;
import com.appledeveloperacademy.MC2Server.dto.request.CreateCatReq;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
class RoomServiceV1Test {
    @Autowired EntityManager em;
    @Autowired RoomServiceV1 roomService;

    @Autowired RoomRepository roomRepository;

    @Test
    void createRoomTest() {
        // given
        Member member = new Member();
        CreateCatReq createCatReq = new CreateCatReq();
        createCatReq.setGender("FEMALE");
        createCatReq.setAge(3);

        // when
        em.persist(member);
        System.out.println("아이디는 " + member.getId());
        Long room = roomService.createRoom(member.getId(), createCatReq);

        em.flush();
        MemberRoom room1 = em.find(MemberRoom.class, room);

        // then
        assertEquals(member, room1.getMember());
        assertEquals(Gender.FEMALE, room1.getCat().getGender());
    }

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

    @Test
    void invitationNullTest() {
        // given
        Invitation invitation = new Invitation();
        Room room = new Room();
        Room room2 = new Room();
        room.setInvitation(invitation);
        invitation.setRoom(room);


        em.persist(room);
        em.persist(room2);

        // when
        em.flush();

        Room room1 = em.find(Room.class, room.getId());
        Room room3 = em.find(Room.class, room2.getId());

        // then
        assertEquals(invitation, room1.getInvitation());
        assertNull(room3.getInvitation());
    }

    @Test
    void createInvitationTest() {
        // given
        Room room = new Room();
        em.persist(room);

        // when
        Long invitationCode = roomService.createInvitation(room.getId());
        em.flush();

        Room room1 = em.find(Room.class, room.getId());
        Invitation invitation = em.find(Invitation.class, invitationCode);

        // then
        assertEquals(invitation, room1.getInvitation());
    }

    @Test
    void createInvitationTest_expired() {
        // given
        Room room = new Room();
        Invitation invitation = new Invitation();
        invitation.setExpiredAt(LocalDateTime.now().minusDays(1));
        room.addInvitation(invitation);
        em.persist(room);

        // when
        Long invitation2 = roomService.createInvitation(room.getId());

        em.flush();

        Room room1 = em.find(Room.class, room.getId());
        Invitation invitation3 = em.find(Invitation.class, invitation2);
        em.flush();

        // then
        Invitation invitation1 = room1.getInvitation();
        assertEquals(invitation1, invitation3);
    }

    @Test
    void createInivtationTestV2() {
        Room room = new Room();
        Invitation invitation = new Invitation();
        room.addInvitation(invitation);
        em.persist(room);

        Room findRoom = em.find(Room.class, room.getId());


        assertEquals(invitation, findRoom.getInvitation());
    }

    @Test
    void participateRoomTest() {
        // given
        Member member = new Member();
        Room room = new Room();
        CreateCatReq createCatReq = new CreateCatReq();
        createCatReq.setGender("MALE");
        createCatReq.setAge(3);
        Cat cat = new Cat(createCatReq);

        em.persist(member);
        em.persist(room);
        em.flush();

        // when
        Long memberRoomId = roomService.participateRoom(member.getId(), room.getId(), createCatReq);
        System.out.println("memberRoomId = " + memberRoomId);
        MemberRoom memberRoom = em.find(MemberRoom.class, memberRoomId);
        Cat cat1 = memberRoom.getCat();

        // then
        assertEquals(member,memberRoom.getMember());
        assertEquals(room, memberRoom.getRoom());
        assertEquals(cat.getGender(), cat1.getGender());
    }
}