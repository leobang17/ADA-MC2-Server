package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.*;
import com.appledeveloperacademy.MC2Server.dto.request.CreateCatReq;
import com.appledeveloperacademy.MC2Server.repository.RoomRepository;
import com.appledeveloperacademy.MC2Server.repository.UserRepository;
import com.appledeveloperacademy.MC2Server.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Qualifier(value = "roomServiceV1")
public class RoomServiceV1 implements RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;


    @Override
    public Invitation findInvitationCodeByRoomId(Long roomId) {
        // invitation code 있을 때 정상
        // 없을 때 exception throw
        return roomRepository.getInvitationByRoomId(roomId);
    }

    @Override
    public List<MemberRoom> findAllParticipatingRooms(Long userId) {
        return roomRepository.listMemberRoomByUserId(userId);
    }

    @Override
    public Cat findCatByCatId(Long catId) {
        return roomRepository.findCatById(catId);
    }

    @Override
    @Transactional
    public Long createRoom(Long userId, CreateCatReq createCatReq) {
        // fetch user by id
        Member findUser = userRepository.findById(userId);

        // create instances
        Room room = new Room();
        Cat cat = new Cat(createCatReq);
        MemberRoom memberRoom = MemberRoom.createMemberRoom(findUser, room, cat);

        // persist
        roomRepository.createRoom(room);

        return memberRoom.getId();
    }

    @Override
    @Transactional
    public Long participateRoom(Long userId, Long roomId, CreateCatReq createCatReq) {
        Member findUser = userRepository.findById(userId);
        Room findRoom = roomRepository.findRoomByRoomId(roomId);

        Cat cat = new Cat(createCatReq);
        MemberRoom memberRoom = MemberRoom.createMemberRoom(findUser, findRoom, cat);

        roomRepository.createMemberRoom(memberRoom);

        return memberRoom.getId();
    }

    @Override
    @Transactional
    public Long createInvitation(Long roomId) {
        // get room by roomId
        Room room = roomRepository.findRoomByRoomId(roomId);

        // check if room has invitation
        Invitation invitation = room.getInvitation();

        if (invitation != null) {
            if (!invitation.isExpired()) {
                // invitation exists and invitation is still available.
                // Do: throw Error

            } else {
                // invitation exists but invitation is expired.
                // Do: Delete invitation
                room.removeInvitation(invitation);
                roomRepository.removeInvitation(invitation);
            }
        }

        // Invitation doesn't exist or is expired
        // Do: Create Invitation
        String code;
        do {
            code = Invitation.generateCode();
        } while (roomRepository.getInvitationByCode(code).size() > 0);

        Invitation createdInvitation = new Invitation(code);
        room.addInvitation(createdInvitation);

        // Persist created invitation -> createdInvitation.getId()를 return하기 위해서는 persistence context에 올려야함.
        roomRepository.createInvitation(createdInvitation);

        return createdInvitation.getId();
    }


}
