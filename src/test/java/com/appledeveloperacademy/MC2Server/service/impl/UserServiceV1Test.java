package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.repository.UserRepository;
import com.appledeveloperacademy.MC2Server.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceV1Test {
    @Autowired EntityManager em;
    @Autowired UserServiceV1 service;

    @Autowired UserRepository userRepository;

    @Test
    void findUserByUserCode() {
        // given
        Member member = new Member();
        member.setUsercode("123456");
        em.persist(member);

        // when
        em.flush();
        Member userByUserCode = service.findUserByUserCode(member.getUsercode());

        // then
        assertEquals(member, userByUserCode);
    }

    @Test
    void findHealthTagsByUserId() {
        // given
        HealthTag healthTag = new HealthTag();
        HealthTag healthTag2 = new HealthTag();
        Member member = new Member();
        member.addHealthTags(healthTag, healthTag2);
        em.persist(member);

        // when
        em.flush();
        List<HealthTag> healthTagsByUserId = service.findHealthTagsByUserId(member.getId());

        // then
        assertEquals(2, healthTagsByUserId.size());
    }
}