package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.domain.log.Log;
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

        assertThrows(RuntimeException.class, () -> {
            Long isAuthed2 = tokenService.authenticate("Bearer abcdefff");

        });
        assertEquals(member.getId(), isAuthed);
    }
}