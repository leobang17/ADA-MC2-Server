package com.appledeveloperacademy.MC2Server.repository;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.Member;

import com.appledeveloperacademy.MC2Server.domain.MemberRoom;
import com.appledeveloperacademy.MC2Server.domain.Room;
import org.junit.Assert;
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
class UserRepositoryTest {
    @Autowired EntityManager em;
    @Autowired UserRepository userRepository;

    @Test
    void save() {
        // given
        Member member = createMember();
        userRepository.save(member);
        Long id = member.getId();

        // when
        em.flush();
        Member byId = userRepository.findById(id);

        // then
        assertEquals(member, byId);
        System.out.println("byId.getCreatedAt() = " + byId.getCreatedAt());
    }

    @Test
    void findByUsercode() {
        // given
        Member member = createMember();
        userRepository.save(member);

        // when
        List<Member> byUsercode = userRepository.findByUsercode(member.getUsercode());

        // then
        assertEquals(member.getUsercode(), byUsercode.get(0).getUsercode());
    }

    @Test
    void listTags() {
        // given 
        Member member = createMember();
        HealthTag tag1 = new HealthTag();
        tag1.setContent("뿡");
        HealthTag tag2 = new HealthTag();
        tag2.setContent("뽕");
//        tag1.addHealthTag(member);
//        tag2.addHealthTag(member);
        member.addHealthTag(tag1);
        member.addHealthTag(tag2);
        em.persist(member);
        
        // when
        Long id = member.getId();
        List<HealthTag> tags = userRepository.listHealthTagsById(id);

        // then
        assertEquals(2, tags.size());
        for (HealthTag tag : tags) {
            System.out.println("tag.getContent() = " + tag.getContent());
        }
//        assertEquals(member.getHealthTags(), tags);

    }

    @Test
    void findByUsercodeFetchRoom() {
        // given
        Member member = new Member();
        member.setUsercode("123456");
        Room room = new Room();
        MemberRoom memberRoom = new MemberRoom();
        room.addMemberRoom(memberRoom);
        member.addMemberRoom(memberRoom);

        em.persist(room);
        em.persist(member);

        // when
        em.flush();
        List<Member> byUsercodeFetchRoom = userRepository.findByUsercode(member.getUsercode());

        // then
        assertEquals(member, byUsercodeFetchRoom.get(0));
    }

    @Test
    void listHealthTagsByTagContent() {
        // given
        Member member = new Member();
        HealthTag healthTag = new HealthTag();
        HealthTag healthTag1 = new HealthTag();
        healthTag.setContent("태그");
        healthTag1.setContent("태그1");

        member.addHealthTags(healthTag, healthTag1);

        em.persist(member);

        // when
        em.flush();
        List<HealthTag> tags = userRepository.listHealthTagsByTagContent(member.getId(), "태그");
        List<HealthTag> tags1 = userRepository.listHealthTagsByTagContent(member.getId(), "태그4");
        List<HealthTag> tags2 = userRepository.listHealthTagsById(member.getId());
        
        // then
        assertEquals(1, tags.size());
        assertEquals(0, tags1.size());
        for (HealthTag tag : tags2) {
            System.out.println("tag.getContent() = " + tag.getContent());
        }
    }

    private Member createMember() {
        Member member = new Member();
        member.setUsername("leo");
        String s = UUID.randomUUID().toString();
        member.setUsercode(s);
        return member;
    }

}