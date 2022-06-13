package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.Cat;
import com.appledeveloperacademy.MC2Server.domain.Invitation;
import com.appledeveloperacademy.MC2Server.domain.MemberRoom;
import com.appledeveloperacademy.MC2Server.dto.InvitationCodeDto;
import com.appledeveloperacademy.MC2Server.repository.RoomRepository;
import com.appledeveloperacademy.MC2Server.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Qualifier(value = "roomServiceV1")
public class RoomServiceV1 implements RoomService {
    private final RoomRepository roomRepository;


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
}
