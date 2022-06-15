package com.appledeveloperacademy.MC2Server.repository;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.HealthTagActivated;
import com.appledeveloperacademy.MC2Server.domain.Member;
import com.appledeveloperacademy.MC2Server.domain.Room;
import com.appledeveloperacademy.MC2Server.domain.log.DietLog;
import com.appledeveloperacademy.MC2Server.domain.log.Log;
import com.appledeveloperacademy.MC2Server.domain.log.MemoLog;
import com.appledeveloperacademy.MC2Server.domain.log.WaterLog;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class LogRepositoryTest {
    @Autowired EntityManager em;
    @Autowired LogRepository logRepository;


    @Test
    void findDietLog() {
        // given 
        DietLog dietLog = new DietLog();
        dietLog.setPublic(true);
        Member member = new Member();
        Room room = new Room();
        dietLog.setMember(member);
        room.addLog(dietLog);
        em.persist(room);
        em.persist(member);


        // when 
        em.flush();
        DietLog dietLogByRoomId = logRepository.findDietLogByRoomId(room.getId(), false);

        // then
        assertEquals(member, dietLogByRoomId.getMember());
        System.out.println("dietLogByRoomId.getCreatedAt() = " + dietLogByRoomId.getCreatedAt());
    }

    @Test
    void downcastTest() {
        DietLog dietLog = new DietLog();
        Room room = new Room();
        room.addLog(dietLog);
        em.persist(room);

        em.flush();

        DietLog singleResult = em.createQuery(
                        "SELECT d" +
                                " FROM DietLog d" +
                                " JOIN d.room r" +
                                " WHERE r.id = :id", DietLog.class)
                .setParameter("id", room.getId())
                .getSingleResult();
    }

    @Test
    void getDietLogsTest() {
        // given
        DietLog dietLog = new DietLog();
        dietLog.setPublic(true);
        DietLog dietLog1 = new DietLog();
        dietLog1.setPublic(true);
        DietLog dietLog2 = new DietLog();
        dietLog2.setPublic(false);
        WaterLog waterLog = new WaterLog();
        waterLog.setPublic(true);
        MemoLog memoLog = new MemoLog();


        Room room = new Room();
        room.addLog(dietLog);
        room.addLog(dietLog1);
        room.addLog(dietLog2);
        room.addLog(waterLog);
        room.addLog(memoLog);

        em.persist(room);
        em.flush();

        // when
        List<Log> waterLogs = logRepository.getLogsByRoomId(room.getId(), LogType.WATER, false, 0, 10);
        List<Log> dietLogsPublic = logRepository.getLogsByRoomId(room.getId(), LogType.DIET, false, 0, 10);
        List<Log> dietLogsPrivate = logRepository.getLogsByRoomId(room.getId(), LogType.DIET, true, 0, 10);

        // then
        assertEquals(1, waterLogs.size());
        assertEquals(2, dietLogsPublic.size());
        assertEquals(1, dietLogsPrivate.size());
    }

    @Test
    void listHealthTagsActivated() {
        // given
        HealthTag healthTag = new HealthTag();
        healthTag.setContent("태그1");
        HealthTag healthTag1 = new HealthTag();
        healthTag1.setContent("태그2");
        HealthTag healthTag2 = new HealthTag();
        healthTag2.setContent("태그3");

        Room room = new Room();
        em.persist(healthTag);
        em.persist(healthTag1);
        em.persist(healthTag2);
        em.persist(room);

        em.flush();

        // when
        Room findRoom = em.find(Room.class, room.getId());
        HealthTag findTag1 = em.find(HealthTag.class, healthTag.getId());
        HealthTag findTag2 = em.find(HealthTag.class, healthTag1.getId());
        HealthTag findTag3 = em.find(HealthTag.class, healthTag2.getId());

        HealthTagActivated healthTagActivated = new HealthTagActivated();
        HealthTagActivated healthTagActivated1 = new HealthTagActivated();
        HealthTagActivated healthTagActivated2 = new HealthTagActivated();

        healthTagActivated.activate(findTag1, findRoom);
        healthTagActivated1.activate(findTag2, findRoom);
        healthTagActivated2.activate(findTag3, findRoom);

        em.flush();

        List<HealthTag> tags = logRepository.listHealthTagsActivated(room.getId());

        assertEquals(3, tags.size());
        assertEquals("태그1", tags.get(0).getContent());
        assertEquals("태그2", tags.get(1).getContent());
        assertEquals("태그3", tags.get(2).getContent());
    }

    @Test
    void activatedLogRemovalTest() {
        HealthTag healthTag = new HealthTag();
        healthTag.setContent("태그1");
        HealthTag healthTag1 = new HealthTag();
        healthTag1.setContent("태그2");
        HealthTag healthTag2 = new HealthTag();
        healthTag2.setContent("태그3");

        Room room = new Room();
        em.persist(healthTag);
        em.persist(healthTag1);
        em.persist(healthTag2);
        em.persist(room);

        em.flush();

        Room findRoom = em.find(Room.class, room.getId());
        HealthTag findTag1 = em.find(HealthTag.class, healthTag.getId());
        HealthTag findTag2 = em.find(HealthTag.class, healthTag1.getId());
        HealthTag findTag3 = em.find(HealthTag.class, healthTag2.getId());

        HealthTagActivated healthTagActivated = new HealthTagActivated();
        HealthTagActivated healthTagActivated1 = new HealthTagActivated();
        HealthTagActivated healthTagActivated2 = new HealthTagActivated();

        healthTagActivated.activate(findTag1, findRoom);
        healthTagActivated1.activate(findTag2, findRoom);
        healthTagActivated2.activate(findTag3, findRoom);

        em.flush();
        List<HealthTag> tags = logRepository.listHealthTagsActivated(room.getId());
        assertEquals(3, tags.size());

        Room findRoom2 = em.find(Room.class, room.getId());
        HealthTagActivated findActivatedTag = em.find(HealthTagActivated.class, healthTagActivated.getId());

        findRoom2.removeActivatedTag(findActivatedTag);

        em.remove(findActivatedTag);
        em.flush();
        List<HealthTag> tags1 = logRepository.listHealthTagsActivated(room.getId());

        HealthTagActivated findActivatedTag2 = em.find(HealthTagActivated.class, findActivatedTag.getId());

        assertEquals(2, tags1.size());
        assertNull(findActivatedTag2);
    }

    @Test
    void findHealthTagActivateTest() {
        // given
        Room room = new Room();
        HealthTag healthTag = new HealthTag();
        HealthTagActivated healthTagActivated = new HealthTagActivated();

        healthTagActivated.activate(healthTag, room);

        em.persist(room);
        em.persist(healthTag);

        // when
        em.flush();
        HealthTagActivated find = logRepository.findHealthTagActivate(room.getId(), healthTag.getId());

        // then
        assertEquals(healthTagActivated, find);
    }

}