package com.appledeveloperacademy.MC2Server.repository;

import com.appledeveloperacademy.MC2Server.domain.MemberRoom;
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
    void getDietLogsTest() {
        // given
        DietLog dietLog = new DietLog();
        DietLog dietLog1 = new DietLog();
        DietLog dietLog2 = new DietLog();
        WaterLog waterLog = new WaterLog();
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
        List<Log> waterLogs = logRepository.getLogsByRoomId(room.getId(), LogType.WATER);
        List<Log> dietLogs = logRepository.getLogsByRoomId(room.getId(), LogType.DIET);

        // then
        assertEquals(1, waterLogs.size());
        assertEquals(3, dietLogs.size());
    }

}