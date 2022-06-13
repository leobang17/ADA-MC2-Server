package com.appledeveloperacademy.MC2Server.repository;

import com.appledeveloperacademy.MC2Server.domain.*;
import com.appledeveloperacademy.MC2Server.domain.log.Log;
import com.appledeveloperacademy.MC2Server.dto.InvitationCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepository {
    private final EntityManager em;

    // 아예 새 room 생성 + cat
    public void createRoom(Room room) {
        em.persist(room);
    }

    public void addMemberToRoom(Room room, Member member) {
        // service 에서 room.addMemberRoom  (MemberRoom은 cat이랑 member 서로 연결되어있는 상태)
    }

    public List<Invitation> getInvitationByCode(String invitationCode) {
        return em.createQuery(
                        "Select i" +
                                " FROM Invitation i" +
                                " WHERE i.code = :code", Invitation.class)
                .setParameter("code", invitationCode)
                .getResultList();
    }

    public Invitation getInvitationByRoomId(Long roomId) {
        return em.createQuery(
                        "SELECT i" +
                                " FROM Room r" +
                                " JOIN r.invitation i" +
                                " WHERE r.id = :id", Invitation.class)
                .setParameter("id", roomId)
                .getSingleResult();
    }

    public Cat findCatById(Long catId) {
        return em.find(Cat.class, catId);
    }

    public List<MemberRoom> listMemberRoomByUserId(Long userId) {
        return em.createQuery(
                        "Select mr " +
                                "FROM Member m " +
                                "JOIN m.memberRooms mr " +
                                "WHERE m.id = :userId", MemberRoom.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Room findRoomByRoomId(Long roomId) {
        return em.createQuery(
                        "SELECT r" +
                                " FROM Room r" +
                                " WHERE r.id = :id", Room.class)
                .setParameter("id", roomId)
                .getSingleResult();
    }

    public void removeInvitation(Invitation invitation) {
        em.remove(invitation);
    }

    public void createInvitation(Invitation invitation) {
        em.persist(invitation);
    }

    public void createMemberRoom(MemberRoom memberRoom) {
        em.persist(memberRoom);
    }

}
