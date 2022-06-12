package com.appledeveloperacademy.MC2Server.repository;

import com.appledeveloperacademy.MC2Server.domain.log.DietLog;
import com.appledeveloperacademy.MC2Server.domain.log.Log;
import com.appledeveloperacademy.MC2Server.domain.log.SnackLog;
import com.appledeveloperacademy.MC2Server.domain.log.WaterLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LogRepository {

    private final EntityManager em;

    public List<Log> getLogsByRoomId(Long roomId, LogType type, boolean isPrivate, int offset, int limit) {
        return em.createQuery(logQueryGenerator(roomId, type) + " AND l.isPublic = :public", Log.class)
                .setParameter("public", !isPrivate)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public DietLog findDietLogByRoomId(Long roomId, boolean isPrivate) {
        return em.createQuery(
                        "SELECT l" +
                                " FROM DietLog l" +
                                " JOIN l.room r" +
                                " JOIN FETCH l.member m" +
                                " WHERE r.id = :id" +
                                " AND l.isPublic = :public", DietLog.class)
                .setParameter("id", roomId)
                .setParameter("public", !isPrivate)
                .getSingleResult();
    }

    public WaterLog findWaterLogByRoomId(Long roomId, boolean isPrivate) {
        return em.createQuery(
                        "SELECT l" +
                                " FROM WaterLog l" +
                                " JOIN l.room r" +
                                " JOIN FETCH l.member m" +
                                " WHERE r.id = :id" +
                                " AND l.isPublic = :public", WaterLog.class)
                .setParameter("id", roomId)
                .setParameter("public", !isPrivate)
                .getSingleResult();
    }

    public SnackLog findSnackLogByRoomId(Long roomId, boolean isPrivate) {
        return em.createQuery(
                        "SELECT l" +
                                " FROM SnackLog l" +
                                " JOIN l.room r" +
                                " JOIN FETCH l.member m" +
                                " WHERE r.id = :id" +
                                " AND l.isPublic = :public", SnackLog.class)
                .setParameter("id", roomId)
                .setParameter("public", !isPrivate)
                .getSingleResult();
    }

    private String logQueryGenerator(Long roomId, LogType type) {
        return "SELECT l" +
                " FROM Room r" +
                " JOIN r.logs l" +
                " WHERE TYPE(l) = " + type.tableNameResolver() +
                " AND r.id = " + roomId.toString();
    }


}
