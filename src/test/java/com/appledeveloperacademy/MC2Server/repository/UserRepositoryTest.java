package com.appledeveloperacademy.MC2Server.repository;

import com.appledeveloperacademy.MC2Server.domain.Member;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired EntityManager em;
    @Autowired UserRepository userRepository;

    @Test
    void save() {
        // given
        Member member = new Member();
        member.setUsername("leo");
        String s = UUID.randomUUID().toString();
        member.setUsercode(s);
        userRepository.save(member);
        Long id = member.getId();

        // when
//        em.flush();
        Member byId = userRepository.findById(id);

        // then
        assertEquals(member, byId);
    }

    @Test
    void findByUsercode() {
        // given
        Member member = new Member();
        member.setUsername("leo");
        String s = UUID.randomUUID().toString();
        member.setUsercode(s);
        userRepository.save(member);

        // when
        Member byUsercode = userRepository.findByUsercode(s);

        // then
        assertEquals(member.getUsercode(), byUsercode.getUsercode());
    }
}