package com.appledeveloperacademy.MC2Server.service;

import com.appledeveloperacademy.MC2Server.domain.Cat;
import com.appledeveloperacademy.MC2Server.dto.InvitationCodeDto;
import com.appledeveloperacademy.MC2Server.dto.ParticipatingRoomDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    InvitationCodeDto findInvitationCodeByRoomId(Long roomId);

    List<ParticipatingRoomDto> findAllParticipatingRooms(Long userId);

    Cat findCatByCatId(Long catId);
}
