package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.domain.MemberRoom;
import com.appledeveloperacademy.MC2Server.domain.Room;
import com.appledeveloperacademy.MC2Server.domain.log.Log;
import com.appledeveloperacademy.MC2Server.exception.CustomException;
import org.hibernate.annotations.common.reflection.XMember;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class TokenServiceTest {

    @Autowired TokenService tokenService;
    @Autowired EntityManager em;

    @Test
    void authenticate() {
        Member member = new Member();
        member.setUsercode("abcdef");

        em.persist(member);

        Long isAuthed = tokenService.authenticate("Bearer abcdef");

        assertThrows(CustomException.class, () -> {
            Long isAuthed2 = tokenService.authenticate("Bearer abcdefff");
        });
        assertEquals(member.getId(), isAuthed);
    }

    @Test
    void authorize() {
        Member member = new Member();
        member.setUsercode("123456");
        Room room = new Room();
        Member member1 = new Member();
        member.setUsercode("6789");
        MemberRoom memberRoom = new MemberRoom();
        MemberRoom memberRoom1 = new MemberRoom();
        MemberRoom memberRoom2 = new MemberRoom();

        member.addMemberRoom(memberRoom);
        room.addMemberRoom(memberRoom);

        member1.addMemberRoom(memberRoom1);
        member1.addMemberRoom(memberRoom2);
        room.addMemberRoom(memberRoom1);
        room.addMemberRoom(memberRoom2);

        em.persist(member);
        em.persist(room);
        em.persist(member1);

        em.flush();

        assertThrows(CustomException.class, () -> {
            tokenService.authenticateAndAuthorize("Bearer 123456", 12L);
        });

//        assertThrows(CustomException.class, () -> {
//            tokenService.authenticateAndAuthorize("Bearer 6789", room.getId());
//        });
    }
}