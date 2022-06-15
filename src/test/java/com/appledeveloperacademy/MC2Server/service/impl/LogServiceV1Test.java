package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.*;
import com.appledeveloperacademy.MC2Server.domain.enums.HealthLogAction;
import com.appledeveloperacademy.MC2Server.domain.log.*;
import com.appledeveloperacademy.MC2Server.dto.log.SummerizedLogDto;
import com.appledeveloperacademy.MC2Server.dto.request.CreateDietReq;
import com.appledeveloperacademy.MC2Server.dto.request.CreateHealthReq;
import com.appledeveloperacademy.MC2Server.dto.request.CreateMemoReq;
import com.appledeveloperacademy.MC2Server.dto.request.CreateWaterReq;
import com.appledeveloperacademy.MC2Server.repository.LogRepository;
import com.appledeveloperacademy.MC2Server.repository.LogType;
import com.appledeveloperacademy.MC2Server.service.RoomService;
import org.hibernate.boot.model.source.spi.EmbeddableMapping;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class LogServiceV1Test {

    @Autowired LogServiceV1 logServiceV1;
    @Autowired LogRepository logRepository;
    @Autowired EntityManager em;

    @Test
    void getDietLogs() {
        // given
        Member member = new Member();
        Room room = new Room();
        DietLog dietLog = new DietLog();
        DietLog dietLog1 = new DietLog();
        DietLog dietLog2 = new DietLog();
        DietLog dietLog3 = new DietLog();
        DietLog dietLog4 = new DietLog();
        WaterLog waterLog = new WaterLog();

        dietLog.writeLog(member, room);
        dietLog1.writeLog(member, room);
        dietLog2.writeLog(member, room);
        dietLog3.writeLog(member, room);
        dietLog4.writeLog(member, room);
        waterLog.writeLog(member, room);

        em.persist(member);
        em.persist(room);

        em.flush();

        // when
        List<DietLog> dietLogs = logServiceV1.getDietLogs(member.getId(), room.getId(), false, 10, 0);
        List<WaterLog> waterLogs = logServiceV1.getWaterLogs(member.getId(), room.getId(), false, 10, 0);

        //then
        assertEquals(5, dietLogs.size());
        assertEquals(1, waterLogs.size());
    }

    @Test
    void getWaterLogs() {
        // given
        Member member = new Member();
        Room room = new Room();
        WaterLog waterLog = new WaterLog();
        waterLog.writeLog(member, room);

        em.persist(member);
        em.persist(room);

        // when
        em.flush();
        List<WaterLog> waterLogs = logServiceV1.getWaterLogs(member.getId(), room.getId(), true, 10, 0);

        // then
        assertEquals(1, waterLogs.size());
    }

    @Test
    void getHealthLogsWithOrderBy() {
        Member member = new Member();
        Room room = new Room();

        HealthLog healthLog = new HealthLog();
        HealthLog healthLog1 = new HealthLog();
        HealthLog healthLog2 = new HealthLog();
        HealthLog healthLog3 = new HealthLog();
        HealthLog healthLog4 = new HealthLog();

        healthLog.writeLog(member, room);
        healthLog1.writeLog(member, room);
        healthLog2.writeLog(member, room);
        healthLog3.writeLog(member, room);
        healthLog4.writeLog(member, room);

        em.persist(member);
        em.persist(room);

        em.flush();

        List<HealthLog> healthLogs = logServiceV1.getHealthLogs(member.getId(), room.getId(), false, 3, 0);
        HealthLog healthLog5 = em.find(HealthLog.class, healthLog.getId());
        List<Log> logs = em.createQuery(
                        "SELECT l" +
                                " FROM Room r" +
                                " JOIN r.logs l" +
//                                " WHERE TYPE(l) = HealthLog" +
                                " WHERE r.id = :id" +
                                " ORDER BY l.id ASC", Log.class)
                .setParameter("id", room.getId())
                .getResultList();

        List<Log> logs2 = em.createQuery(
                        "SELECT l" +
                                " FROM Room r" +
                                " JOIN r.logs l" +
//                                " WHERE TYPE(l) = HealthLog" +
                                " WHERE r.id = :id" +
                                " ORDER BY l.id DESC", Log.class)
                .setParameter("id", room.getId())
                .getResultList();

        List<HealthLog> collect = logs.stream().map(log -> (HealthLog) log).collect(Collectors.toList());
        List<HealthLog> collect1 = logs2.stream().map(log -> (HealthLog) log).collect(Collectors.toList());

        assertEquals(5, collect.size());
        assertEquals(healthLog, collect.get(0));
        assertEquals(healthLog1, collect.get(1));;
        assertEquals(healthLog4, collect1.get(0));

        System.out.println("======= orderby - createdAt Ascending ======");
        for (HealthLog log : collect) {
            System.out.println("log.getId() = " + log.getId());
            System.out.println("log.getCreatedAt() = " + log.getCreatedAt());
        }

        System.out.println("======= orderby - createdAt Descending ======");
        for (HealthLog log : collect1) {
            System.out.println("log.getId() = " + log.getId());
            System.out.println("log.getCreatedAt() = " + log.getCreatedAt());
        }
    }

    @Test
    void getHealthLogs() {
        // given
        Member member = new Member();
        Room room = new Room();

        HealthLog healthLog = new HealthLog();
        HealthLog healthLog1 = new HealthLog();

        healthLog.writeLog(member,room);
        healthLog1.writeLog(member, room);

        em.persist(member);
        em.persist(room);

        // when
        em.flush();
        List<HealthLog> healthLogs = logServiceV1.getHealthLogs(member.getId(), room.getId(), false, 10, 0);

        // then
        assertEquals(2, healthLogs.size());
    }

    @Test
    void createDietLogTest() {
        // given
        Member member = new Member();
        Room room = new Room();
        em.persist(member);
        em.persist(room);

        em.flush();

        // when
        Long dietLog = logServiceV1.createDietLog(member.getId(), room.getId(), new CreateDietReq());
        Long dietLog1 = logServiceV1.createDietLog(member.getId(), room.getId(), new CreateDietReq());

        DietLog dietLog2 = em.find(DietLog.class, dietLog);
        DietLog dietLog3 = em.find(DietLog.class, dietLog1);

        // then
        assertNotNull(dietLog2);
        assertNotNull(dietLog3);
    }

    @Test
    void createWaterLogTest() {
        // given
        Member member = new Member();
        Room room = new Room();
        em.persist(member);
        em.persist(room);

        em.flush();

        // when
        Long waterLog = logServiceV1.createWaterLog(member.getId(), room.getId(), new CreateWaterReq());
        Long waterLog1 = logServiceV1.createWaterLog(member.getId(), room.getId(), new CreateWaterReq());

        WaterLog waterLog2 = em.find(WaterLog.class, waterLog);
        WaterLog waterLog3 = em.find(WaterLog.class, waterLog1);

        // then
        assertNotNull(waterLog2);
        assertNotNull(waterLog3);
    }



    @Test
    void getMemoLogs() {
        Member member = new Member();
        Room room = new Room();

        MemoLog memoLog = new MemoLog();
        MemoLog memoLog1 = new MemoLog();
        MemoLog memoLog2 = new MemoLog();
        MemoLog memoLog3 = new MemoLog();
        MemoLog memoLog4 = new MemoLog();

        memoLog.writeLog(member, room);
        memoLog1.writeLog(member, room);
        memoLog2.writeLog(member, room);
        memoLog3.writeLog(member, room);
        memoLog4.writeLog(member, room);

        em.persist(member);
        em.persist(room);

        em.flush();

        List<MemoLog> memoLogs = logServiceV1.getMemoLogs(member.getId(), room.getId(), true, 10, 0);

        for (MemoLog log : memoLogs) {
            System.out.println("log.isPublic() = " + log.isPublic());
        }

        assertEquals(5, memoLogs.size());
    }

    @Test
    void getSummerizedLogs() {


    }


    @Test
    void createDietLog() {
        Member member = new Member();
        Room room = new Room();
        DietLog dietLog = new DietLog();

        dietLog.writeLog(member, room);

        em.persist(member);
        em.persist(room);

        Member member1 = em.find(Member.class, member.getId());
        Room room1 = em.find(Room.class, room.getId());
        DietLog dietLog1 = em.find(DietLog.class, dietLog.getId());


        assertEquals(member, member1);
        assertEquals(dietLog, dietLog1);
        assertEquals(room, room1);
    }

    @Test
    void createMemoLogTest() {
        // given
        Member member = new Member();
        Room room = new Room();

        em.persist(member);
        em.persist(room);
        em.flush();

        CreateMemoReq createMemoReq = new CreateMemoReq();
        createMemoReq.setContent("안녕하세요!");

        // when
        Long memoLog = logServiceV1.createMemoLog(member.getId(), room.getId(), createMemoReq);
        MemoLog memoLog1 = em.find(MemoLog.class, memoLog);

        // then
        assertEquals("안녕하세요!", memoLog1.getContent());
    }

    @Test
    void createHealthLogTest() {
        // create room & member
        Room room = new Room();
        Member member = new Member();

        // create health tag each active & inactive
        HealthTag activeTag = new HealthTag();
        activeTag.setContent("Activated Tag");
        HealthTag inactiveTag = new HealthTag();
        inactiveTag.setContent("Inactive Tag");

        // hook tags to member
        member.addHealthTags(activeTag, inactiveTag);

        // activate active tag
        HealthTagActivated healthTagActivated = new HealthTagActivated();
        healthTagActivated.activate(activeTag, room);

        // persist
        em.persist(member);
        em.persist(room);

        em.flush();

        // when
        CreateHealthReq createHealthReq = new CreateHealthReq();
        createHealthReq.setTagId(activeTag.getId());
        CreateHealthReq createHealthReq1 = new CreateHealthReq();
        createHealthReq1.setTagId(inactiveTag.getId());
        List<CreateHealthReq> idList = new ArrayList<>();
        idList.add(createHealthReq);
        idList.add(createHealthReq1);

        logServiceV1.createHealthLog(member.getId(), room.getId(), idList);

        List<HealthTag> tags = logRepository.listHealthTagsActivated(room.getId());

        List<HealthLog> resultList = em.createQuery(
                "SELECT l" +
                        " FROM HealthLog l", HealthLog.class
        ).getResultList();

        assertEquals(1, tags.size());
        assertEquals("Inactive Tag", tags.get(0).getContent());
        assertEquals(2, resultList.size());
        assertEquals(HealthLogAction.ACTIVATE, resultList.get(1).getAction());
        assertEquals(HealthLogAction.DEACTIVATE, resultList.get(0).getAction());
    }

    @Test
    void getSummarizedLogTest() {
        Room room = new Room();
        Member member = new Member();

        DietLog dietLog = new DietLog();
        dietLog.setCreatedAt(LocalDateTime.now());
        WaterLog waterLog1 = new WaterLog();
        waterLog1.setCreatedAt(LocalDateTime.now());
        WaterLog waterLog = new WaterLog();
        waterLog.setCreatedAt(LocalDateTime.now());
        MemoLog memoLog = new MemoLog();

        dietLog.writeLog(member, room);
        waterLog1.writeLog(member, room);
        waterLog.writeLog(member, room);
        memoLog.writeLog(member, room);

        em.persist(member);
        em.persist(room);


        em.flush();

        SummerizedLogDto summerizedLogs = logServiceV1.getSummerizedLogs(member.getId(), room.getId(), false);
        DietLog dietLog1 = em.find(DietLog.class, summerizedLogs.getDiet().getId());
        WaterLog waterLog2 = em.find(WaterLog.class, summerizedLogs.getWater().getId());

        System.out.println("summerizedLogs.toString() = " + summerizedLogs.toString());
        assertEquals(dietLog, dietLog1);
        assertEquals(waterLog, waterLog2);
    }

    @Test
    void increaseSnackTest() {
        // given
        Member member = new Member();
        Room room = new Room();

        SnackLog snackLog = new SnackLog();
        snackLog.setCount(0);
        snackLog.writeLog(member, room);

        em.persist(member);
        em.persist(room);

        // when
        em.flush();
        logServiceV1.increaseSnack(room.getId(), snackLog.getId());
        logServiceV1.increaseSnack(room.getId(), snackLog.getId());

        SnackLog snackLog1 = em.find(SnackLog.class, snackLog.getId());

        // then
        assertEquals(2, snackLog1.getCount());

        // 2 when - then
        logServiceV1.increaseSnack(room.getId(), snackLog.getId());

        assertEquals(3, snackLog1.getCount());

    }
}