package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.dto.request.CreateUserReq;
import com.appledeveloperacademy.MC2Server.repository.UserRepository;
import com.appledeveloperacademy.MC2Server.service.LogService;
import com.appledeveloperacademy.MC2Server.service.RoomService;
import com.appledeveloperacademy.MC2Server.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
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
    @Autowired
    RoomServiceV1 roomService;

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

    @Test
    void randomSixNumberTest() {
        // given
        String s = (String) ReflectionTestUtils.invokeMethod(roomService,  "generateUsercode");

        // when
        System.out.println(s);
        // then
        assertEquals(6, s.length());
    }

    @Test
    void createUserTest() {
        // given
        CreateUserReq createUserReq = new CreateUserReq();
        createUserReq.setUsername("Leo");
        Member user = service.createUser(createUserReq);

        // when
        em.flush();
        Member member = em.find(Member.class, user.getId());

        // then
        assertEquals("Leo", member.getUsername());
        System.out.println(member.getUsercode());
    }
}