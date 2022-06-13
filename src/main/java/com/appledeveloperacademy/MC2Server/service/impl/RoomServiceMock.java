package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.Cat;
import com.appledeveloperacademy.MC2Server.domain.Coordinate;
import com.appledeveloperacademy.MC2Server.domain.Invitation;
import com.appledeveloperacademy.MC2Server.domain.MemberRoom;
import com.appledeveloperacademy.MC2Server.domain.enums.Gender;
import com.appledeveloperacademy.MC2Server.dto.CatInfoDto;
import com.appledeveloperacademy.MC2Server.dto.InvitationCodeDto;
import com.appledeveloperacademy.MC2Server.dto.ParticipatingRoomDto;
import com.appledeveloperacademy.MC2Server.dto.request.CreateCatReq;
import com.appledeveloperacademy.MC2Server.service.RoomService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier(value = "roomServiceMock")
public class RoomServiceMock implements RoomService {

    @Override
    public Invitation findInvitationCodeByRoomId(Long roomId) {
        return null;
    }

    @Override
    public List<MemberRoom> findAllParticipatingRooms(Long userId) {
        return null;
    }

    @Override
    public Cat findCatByCatId(Long catId) {
        Cat cat = new Cat();
        cat.setId(1L);
        cat.setCoordinate(new Coordinate(10L, 20L));
        cat.setName("초코");
        cat.setNeutralized(true);
        cat.setGender(Gender.MALE);
        cat.setType("코숏");
        cat.setMainImgUrl("https://aaa.bbb.ccc");
        cat.setProfileImgUrl("https://aaa.bbb.ccc");
        return cat;
    }

    @Override
    public Long createRoom(Long userId, CreateCatReq createCatReq) {
        // userid + catInfoDto로 Room 생성 + MemberRoom 생성  -> 연결
        // Cat 생성 + room 과 연결.L
        return null;
    }

    @Override
    public Long createInvitation(Long roomId) {
        return null;
    }
}
