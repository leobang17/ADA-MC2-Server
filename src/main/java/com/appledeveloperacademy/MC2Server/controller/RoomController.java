package com.appledeveloperacademy.MC2Server.controller;

import com.appledeveloperacademy.MC2Server.domain.Cat;
import com.appledeveloperacademy.MC2Server.domain.Invitation;
import com.appledeveloperacademy.MC2Server.domain.MemberRoom;
import com.appledeveloperacademy.MC2Server.domain.log.Log;
import com.appledeveloperacademy.MC2Server.dto.CatInfoDto;
import com.appledeveloperacademy.MC2Server.dto.InvitationCodeDto;
import com.appledeveloperacademy.MC2Server.dto.ParticipatingRoomDto;
import com.appledeveloperacademy.MC2Server.dto.request.CreateCatReq;
import com.appledeveloperacademy.MC2Server.service.RoomService;
import com.appledeveloperacademy.MC2Server.service.impl.TokenService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/rooms")
@RestController
@Slf4j
public class RoomController {

    private final RoomService roomService;
    private final TokenService tokenService;

    public RoomController(@Qualifier(value = "roomServiceV1") RoomService roomService, TokenService tokenService) {
        this.roomService = roomService;
        this.tokenService = tokenService;
    }

    // List all rooms managed by a user
    @GetMapping
    public ResponseEntity<ListedResult<ParticipatingRoomDto>> getAllRooms(
            @RequestHeader(value = "Authorization") String usercode
    ) {
        Long userId = tokenService.authenticate(usercode);
        List<MemberRoom> rooms = roomService.findAllParticipatingRooms(userId);
        List<ParticipatingRoomDto> collect = rooms.stream().map(ParticipatingRoomDto::build)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ListedResult<>(collect));
    }

    // Create a new room
    @PostMapping
    public ResponseEntity createRoom(
            @RequestHeader(value = "Authorization") String usercode,
            @RequestBody CreateCatReq createCatReq) {
        Long userId = tokenService.authenticate(usercode);
        Long roomid = roomService.createRoom(userId, createCatReq);
        return ResponseEntity.created(URI.create("/users/" + userId + "/rooms/" + roomid)).build();
    }

    // Add new user to an existing room
    @PostMapping("/{roomId}/users")
    public ResponseEntity participateRoom(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @RequestBody final CreateCatReq createCatReq) {
        Long userId = tokenService.authenticate(usercode);
        Long memberRoomId = roomService.participateRoom(userId, roomId, createCatReq);
        return ResponseEntity.created(URI.create("/users/" + userId + "/rooms/" + memberRoomId)).build();
    }

    // Check if invitation code exists to specific room.
    @GetMapping("/{roomId}/invitation-codes")
    public ResponseEntity<InvitationCodeDto> checkInvitation(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId
    ) {
        tokenService.authenticate(usercode);
        Invitation invitation = roomService.findInvitationCodeByRoomId(roomId);
        InvitationCodeDto buildDto = InvitationCodeDto.build(invitation);
        return ResponseEntity.ok(buildDto);
    }

    // Create invitation codes for a particular room
    @PostMapping("/{roomId}/invitation-codes")
    public ResponseEntity createInvitation(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId) {
        tokenService.authenticateAndAuthorize(usercode, roomId);
        Long invitationId = roomService.createInvitation(roomId);
        return ResponseEntity.created(URI.create("/rooms/" + roomId  +"/invitation-codes/" + invitationId)).build();
    }

    // Merge a room to another room
    @PostMapping("/{roomId}/rooms")
    public String mergeRoom(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @RequestParam(name = "roomId") final Long mergingRoomId
    ) {
        Long userId = tokenService.authenticateAndAuthorize(usercode, mergingRoomId);
        return "mergeRoom";
    }

    // Get a cat information
    @GetMapping("/{roomId}/cats/{catId}")
    public ResponseEntity<CatInfoDto> getCatProfile(
            @RequestHeader(value = "Authorization") String usercode,
            @PathVariable final Long roomId,
            @PathVariable final Long catId) {
        tokenService.authenticateAndAuthorize(usercode, roomId);
        Cat findCat = roomService.findCatByCatId(catId);
        return ResponseEntity.ok(new CatInfoDto(findCat));
    }
}
