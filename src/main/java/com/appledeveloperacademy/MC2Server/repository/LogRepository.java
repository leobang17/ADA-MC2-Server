package com.appledeveloperacademy.MC2Server.repository;

import com.appledeveloperacademy.MC2Server.domain.log.DietLog;
import com.appledeveloperacademy.MC2Server.domain.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogRepository {

    private final EntityManager em;

    public List<Log> getLogsByRoomId(Long roomId, LogType type) {
        return em.createQuery(logQueryGenerator(roomId, type), Log.class)
                .getResultList();
    }

    private String logQueryGenerator(Long roomId, LogType type) {
        return "SELECT l" +
                " FROM Room r" +
                " JOIN r.logs l" +
                " WHERE TYPE(l) = " + type.tableNameResolver() +
                " AND r.id = " + roomId.toString();
    }


}
