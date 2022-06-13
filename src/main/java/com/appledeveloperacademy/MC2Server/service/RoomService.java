package com.appledeveloperacademy.MC2Server.service;

import com.appledeveloperacademy.MC2Server.domain.Cat;
import com.appledeveloperacademy.MC2Server.domain.Invitation;
import com.appledeveloperacademy.MC2Server.domain.MemberRoom;
import com.appledeveloperacademy.MC2Server.dto.CatInfoDto;
import com.appledeveloperacademy.MC2Server.dto.InvitationCodeDto;
import com.appledeveloperacademy.MC2Server.dto.ParticipatingRoomDto;
import com.appledeveloperacademy.MC2Server.dto.request.CreateCatReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    Invitation findInvitationCodeByRoomId(Long roomId);

    List<MemberRoom> findAllParticipatingRooms(Long userId);

    Cat findCatByCatId(Long catId);

    Long createRoom(Long userId, CreateCatReq createCatReq);
}
