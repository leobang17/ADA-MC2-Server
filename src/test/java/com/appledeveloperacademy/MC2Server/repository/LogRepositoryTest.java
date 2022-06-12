package com.appledeveloperacademy.MC2Server.repository;

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
        List<Log> waterLogs = logRepository.getLogsByRoomId(room.getId(), LogType.WATER, false);
        List<Log> dietLogsPublic = logRepository.getLogsByRoomId(room.getId(), LogType.DIET, false);
        List<Log> dietLogsPrivate = logRepository.getLogsByRoomId(room.getId(), LogType.DIET, true);

        // then
        assertEquals(1, waterLogs.size());
        assertEquals(2, dietLogsPublic.size());
        assertEquals(1, dietLogsPrivate.size());
    }

}