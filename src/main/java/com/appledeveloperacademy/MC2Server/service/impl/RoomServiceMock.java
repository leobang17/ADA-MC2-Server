package com.appledeveloperacademy.MC2Server.service.impl;

import com.appledeveloperacademy.MC2Server.domain.Cat;
import com.appledeveloperacademy.MC2Server.domain.Coordinate;
import com.appledeveloperacademy.MC2Server.domain.enums.Gender;
import com.appledeveloperacademy.MC2Server.dto.CatInfoDto;
import com.appledeveloperacademy.MC2Server.dto.InvitationCodeDto;
import com.appledeveloperacademy.MC2Server.dto.ParticipatingRoomDto;
import com.appledeveloperacademy.MC2Server.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceMock implements RoomService {

    @Override
    public InvitationCodeDto findInvitationCodeByRoomId(Long roomId) {
        return new InvitationCodeDto("123456");
    }

    @Override
    public List<ParticipatingRoomDto> findAllParticipatingRooms(Long userId) {
        List<ParticipatingRoomDto> dtos = new ArrayList<>();
        ParticipatingRoomDto roomDto1 = new ParticipatingRoomDto(0L, 0L, new CatInfoDto(0L, 5, "나비", Gender.MALE.name(), true, "페르시안", "https://aaa.bbb.ccc", "https://aaa.bbb.ccc", new Coordinate(20L, 10L)));
        ParticipatingRoomDto roomDto2 = new ParticipatingRoomDto(1L, 1L, new CatInfoDto(1L, 6, "치즈", Gender.FEMALE.name(), false, "코숏", "https://aaa.bbb.ccc", "https://aaa.bbb.ccc", new Coordinate(10L, 20L)));
        ParticipatingRoomDto roomDto3 = new ParticipatingRoomDto(2L, 2L, new CatInfoDto(2L, 7, "얼룩이", Gender.FEMALE.name(), false, "코숏", "https://aaa.bbb.ccc", "https://aaa.bbb.ccc", new Coordinate(70L, 20L)));
        dtos.add(roomDto1);
        dtos.add(roomDto2);
        dtos.add(roomDto3);
        return dtos;
    }

    @Override
    public Cat findCatByCatId(Long catId) {
        Cat cat = new Cat();
        cat.setId(1L);
        cat.setAge(12);
        cat.setCoordinate(new Coordinate(10L, 20L));
        cat.setName("초코");
        cat.setNeutralized(true);
        cat.setGender(Gender.MALE);
        cat.setType("코숏");
        cat.setMainImgUrl("https://aaa.bbb.ccc");
        cat.setProfileImgUrl("https://aaa.bbb.ccc");
        return cat;
    }
}
